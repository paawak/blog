<?php

namespace swayam\rest;

use \Exception as Exception;
use Psr\Http\Message\ResponseInterface as Response;
use Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Log\LoggerInterface;
use swayam\model\Author;
use swayam\repo\AuthorRepository;

require_once __DIR__ . '/../model/Author.php';
require_once __DIR__ . '/../model/Address.php';

class AuthorController {

    private $authorRepository;
    private $logger;

    public function __construct(AuthorRepository $authorRepository, LoggerInterface $logger) {
        $this->authorRepository = $authorRepository;
        $this->logger = $logger;
    }

    public function getAllAuthors(Request $request, Response $response) {
        $authors = $this->authorRepository->getAllAuthors();
        $authorsAsJson = json_encode($authors, JSON_PRETTY_PRINT);
        $response->getBody()->write($authorsAsJson);
        return $response->withHeader('Content-Type', 'application/json');
    }

    public function getAuthorById(Request $request, Response $response, $authorId) {
        $author = $this->authorRepository->getAuthorById($authorId);
        $payload = json_encode($author, JSON_PRETTY_PRINT);
        $response->getBody()->write($payload);
        return $response->withHeader('Content-Type', 'application/json');
    }

    public function searchAuthorsByCountry(Request $request, Response $response) {
        $country = $request->getQueryParams()["country"];
        $authors = $this->authorRepository->searchAuthorsByCountry($country);
        $payload = json_encode($authors, JSON_PRETTY_PRINT);
        $response->getBody()->write($payload);
        return $response->withHeader('Content-Type', 'application/json');
    }

    public function addNewAuthor(Request $request, Response $response) {
        $authorRequestAsArray = $request->getParsedBody();

        if (!is_array($authorRequestAsArray)) {
            throw new Exception('Invalid body: Could not decode JSON');
        }

        $authorEntity = Author::fromJsonArray($authorRequestAsArray);

        $this->logger->info("Persisting Author:", $authorRequestAsArray);

        $savedAuthor = $this->authorRepository->addNewAuthor($authorEntity);

        $payload = json_encode($savedAuthor, JSON_PRETTY_PRINT);
        $response->getBody()->write($payload);
        return $response->withHeader('Content-Type', 'application/json');
    }

}
