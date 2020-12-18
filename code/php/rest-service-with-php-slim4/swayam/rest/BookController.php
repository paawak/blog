<?php

namespace swayam\rest;

use \Exception as Exception;
use Psr\Http\Message\ResponseInterface as Response;
use Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Log\LoggerInterface;
use swayam\model\Book;
use swayam\repo\BookRepository;

require_once __DIR__ . '/../model/Book.php';

class BookController {

    private $bookRepository;
    private $logger;

    public function __construct(BookRepository $bookRepository, LoggerInterface $logger) {
        $this->bookRepository = $bookRepository;
        $this->logger = $logger;
    }

    public function getAllBooks(Request $request, Response $response) {
        $books = $this->bookRepository->getAllBooks();
        $booksAsJson = json_encode($books, JSON_PRETTY_PRINT);
        $response->getBody()->write($booksAsJson);
        return $response->withHeader('Content-Type', 'application/json');
    }

    public function getBookById(Request $request, Response $response, $bookId) {
        $book = $this->bookRepository->getBookById($bookId);
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

        $this->logger->info("Persisting Book:", array($bookEntity));

        $savedBook = $this->bookRepository->addNewBook($bookEntity);

        $payload = json_encode($savedBook, JSON_PRETTY_PRINT);
        $response->getBody()->write($payload);
        return $response->withHeader('Content-Type', 'application/json');
    }

}
