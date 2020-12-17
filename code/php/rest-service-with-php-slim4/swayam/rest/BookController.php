<?php

namespace swayam\rest;

use \Exception as Exception;
use Doctrine\ORM\EntityManager;
use Psr\Http\Message\ResponseInterface as Response;
use Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Log\LoggerInterface;
use swayam\model\Book;

require_once __DIR__ . '/../model/Book.php';

class BookController {

    private $entityManager;
    private $logger;

    public function __construct(EntityManager $entityManager, LoggerInterface $logger) {
        $this->entityManager = $entityManager;
        $this->logger = $logger;
    }

    public function getAllBook(Request $request, Response $response) {
        $books = $this->entityManager->getRepository(Book::class)->findAll();
        $booksAsJson = json_encode($books, JSON_PRETTY_PRINT);
        $response->getBody()->write($booksAsJson);
        return $response->withHeader('Content-Type', 'application/json');
    }

    public function getBookById(Request $request, Response $response, $bookId) {
        $book = $this->entityManager->getRepository(Book::class)->find($bookId);
        $payload = json_encode($book, JSON_PRETTY_PRINT);
        $response->getBody()->write($payload);
        return $response->withHeader('Content-Type', 'application/json');
    }

    public function addNewBook(Request $request, Response $response) {
        $bookRequestAsArray = $request->getParsedBody();

        if (!is_array($bookRequestAsArray)) {
            throw new Exception('Invalid body: Could not decode JSON');
        }

        $bookEntity = Book::fromJsonArray($bookRequestAsArray);

        $this->logger->info("Persisting Genre:", array($bookEntity));

        $this->entityManager->persist($bookEntity);
        $this->entityManager->flush();

        $payload = json_encode($bookEntity, JSON_PRETTY_PRINT);
        $response->getBody()->write($payload);
        return $response->withHeader('Content-Type', 'application/json');
    }

}
