<?php

namespace swayam\rest;

use \Exception as Exception;
use Doctrine\ORM\EntityManager;
use Psr\Http\Message\ResponseInterface as Response;
use Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Log\LoggerInterface;
use swayam\model\Author;

require_once __DIR__ . '/../model/Author.php';
require_once __DIR__ . '/../model/Address.php';

class AuthorController {

    private $entityManager;
    private $logger;

    public function __construct(EntityManager $entityManager, LoggerInterface $logger) {
        $this->entityManager = $entityManager;
        $this->logger = $logger;
    }

    public function getAllAuthors(Request $request, Response $response) {
        $authors = $this->entityManager->getRepository(Author::class)->findAll();
        $authorsAsJson = json_encode($authors, JSON_PRETTY_PRINT);
        $response->getBody()->write($authorsAsJson);
        return $response->withHeader('Content-Type', 'application/json');
    }

    public function getAuthorById(Request $request, Response $response, $authorId) {
        $authors = $this->entityManager->getRepository(Author::class)->find($authorId);
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

        $this->entityManager->persist($authorEntity);
        $this->entityManager->flush();

        $payload = json_encode($authorEntity, JSON_PRETTY_PRINT);
        $response->getBody()->write($payload);
        return $response->withHeader('Content-Type', 'application/json');
    }

}
