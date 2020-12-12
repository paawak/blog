<?php

namespace com\swayam\ocr\porua\rest;

use Psr\Container\ContainerInterface;
use \Exception as Exception;
use Doctrine\ORM\EntityManager;
use Psr\Log\LoggerInterface;
use Psr\Http\Message\ResponseInterface as Response;
use Psr\Http\Message\ServerRequestInterface as Request;
use com\swayam\ocr\porua\model\Book;
use com\swayam\ocr\porua\model\PageImage;
use com\swayam\ocr\porua\model\OcrWord;

require_once __DIR__ . '/../model/Book.php';
require_once __DIR__ . '/../model/PageImage.php';
require_once __DIR__ . '/../model/OcrWord.php';

class OCRQueryController {

    private $container;
    private $entityManager;
    private $logger;

    public function __construct(ContainerInterface $container, EntityManager $entityManager, LoggerInterface $logger) {
        $this->container = $container;
        $this->entityManager = $entityManager;
        $this->logger = $logger;
    }

    public function getAllBooks(Request $request, Response $response) {
        $books = $this->entityManager->getRepository(Book::class)->findAll();
        $booksAsJson = json_encode($books, JSON_PRETTY_PRINT);
        $response->getBody()->write($booksAsJson);
        return $response->withHeader('Content-Type', 'application/json');
    }

    public function getPageCountInBook(Request $request, Response $response, $bookId) {
        $pageCount = $this->entityManager->getRepository(PageImage::class)->count(array(
            'book' => $bookId
        ));
        $response->getBody()->write("$pageCount");
        return $response->withHeader('Content-Type', 'application/json');
    }

    public function getPagesInBook(Request $request, Response $response) {
        $bookId = $request->getQueryParams()["bookId"];
        $pages = $this->entityManager->getRepository(PageImage::class)->findBy(
                array(
                    'book' => $bookId,
                    'correctionCompleted' => false,
                    'ignored' => false
                ),
                array('pageNumber' => 'ASC')
        );
        $payload = json_encode($pages, JSON_PRETTY_PRINT);
        $response->getBody()->write($payload);
        return $response->withHeader('Content-Type', 'application/json');
    }

    public function getWordsInPage(Request $request, Response $response) {
        $queryParams = $request->getQueryParams();
        $bookId = $queryParams["bookId"];
        $pageImageId = $queryParams["pageImageId"];
        $words = $this->entityManager->getRepository(OcrWord::class)->findBy(
                array(
                    'ocrWordId.bookId' => $bookId,
                    'ocrWordId.pageImageId' => $pageImageId,
                    'ignored' => false
                ),
                array('ocrWordId.wordSequenceId' => 'ASC')
        );
        $payload = json_encode($words, JSON_PRETTY_PRINT);
        $response->getBody()->write($payload);
        return $response->withHeader('Content-Type', 'application/json');
    }

    public function getWordImage(Request $request, Response $response) {
        $queryParams = $request->getQueryParams();
        $bookId = $queryParams["bookId"];
        $pageImageId = $queryParams["pageImageId"];
        $wordSequenceId = $queryParams["wordSequenceId"];
        $pageName = $this->entityManager->getRepository(PageImage::class)->findOneBy(array(
                    'id' => $pageImageId
                ))->getName();

        $ocrWord = $this->entityManager->getRepository(OcrWord::class)->findOneBy(array(
            'ocrWordId.bookId' => $bookId,
            'ocrWordId.pageImageId' => $pageImageId,
            'ocrWordId.wordSequenceId' => $wordSequenceId
        ));

        $imageFullPath = $this->container->get('ocr.imageStore') . $pageName;
        $imageMimeType = $this->getImageMimeType($imageFullPath);

        $wordImage = $this->getWordImageToWrite($imageFullPath, $ocrWord, $imageMimeType);

        $this->writeImage($wordImage, $imageMimeType);

        imagedestroy($wordImage);

        return $response->withHeader('Content-Type', $imageMimeType);
    }

    private function getWordImageToWrite(string $imageFullPath, OcrWord $ocrWord, string $imageMimeType) {
        $sourceX = $ocrWord->getX1();
        $sourceY = $ocrWord->getY1();
        $width = $ocrWord->getX2() - $sourceX;
        $height = $ocrWord->getY2() - $sourceY;

        $this->logger->info("Reading image: {img}",
                array(
                    'img' => $imageFullPath,
                    $sourceX, $sourceY, $width, $height
        ));

        $imageSource = $this->readImage($imageFullPath, $imageMimeType);
        $imageDest = imagecreate($width, $height);
        imagecopyresampled($imageDest, $imageSource, 0, 0, $sourceX, $sourceY, $width, $height, $width, $height);
        imagedestroy($imageSource);
        return $imageDest;
    }

    private function getImageMimeType($imageFullPath) {
        $size = getimagesize($imageFullPath);
        return strtolower($size['mime']);
    }

    private function readImage($imageFullPath, $imageMimeType) {
        switch ($imageMimeType) {
            case 'image/jpg':
            case 'image/jpeg':
                $this->checkImageTypeSupported(IMG_JPG);
                return imagecreatefromjpeg($imageFullPath);
            case 'image/gif':
                $this->checkImageTypeSupported(IMG_GIF);
                return imagecreatefromgif($imageFullPath);
            case 'image/png':
                $this->checkImageTypeSupported(IMG_PNG);
                return imagecreatefrompng($imageFullPath);
            default:
                throw new Exception("This image type" . $imageMimeType . " is not supported, please use another file type", 1);
        }
    }

    private function writeImage($imageToSave, $imageMimeType) {
        switch ($imageMimeType) {
            case 'image/jpg':
            case 'image/jpeg':
                $this->checkImageTypeSupported(IMG_JPG);
                imagejpeg($imageToSave);
                break;
            case 'image/gif':
                $this->checkImageTypeSupported(IMG_GIF);
                imagegif($imageToSave);
                break;
            case 'image/png':
                $this->checkImageTypeSupported(IMG_PNG);
                imagepng($imageToSave);
                break;
            default:
                throw new Exception("This image type" . $imageMimeType . " is not supported, please use another file type", 1);
        }
    }

    private function checkImageTypeSupported($imgType) {
        if (!(imagetypes() & $imgType)) {
            throw new Exception("This image type" . $imgType . " is not supported", 1);
        }
    }

}
