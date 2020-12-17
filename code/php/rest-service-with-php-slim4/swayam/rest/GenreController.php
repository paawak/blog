<?php

namespace swayam\rest;

use \Exception as Exception;
use Doctrine\ORM\EntityManager;
use Psr\Http\Message\ResponseInterface as Response;
use Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Log\LoggerInterface;
use swayam\model\Genre;

require_once __DIR__ . '/../model/Genre.php';

class GenreController {

    private $entityManager;
    private $logger;

    public function __construct(EntityManager $entityManager, LoggerInterface $logger) {
        $this->entityManager = $entityManager;
        $this->logger = $logger;
    }

    public function getAllGenres(Request $request, Response $response) {
        $genres = $this->entityManager->getRepository(Genre::class)->findAll();
        $genresAsJson = json_encode($genres, JSON_PRETTY_PRINT);
        $response->getBody()->write($genresAsJson);
        return $response->withHeader('Content-Type', 'application/json');
    }

    public function getGenreById(Request $request, Response $response, $genreId) {
        $genre = $this->entityManager->getRepository(Genre::class)->find($genreId);
        $payload = json_encode($genre, JSON_PRETTY_PRINT);
        $response->getBody()->write($payload);
        return $response->withHeader('Content-Type', 'application/json');
    }    

    public function addNewGenre(Request $request, Response $response) {
        $genreRequestAsArray = $request->getParsedBody();

        if (!is_array($genreRequestAsArray)) {
            throw new Exception('Invalid body: Could not decode JSON');
        }

        $genreEntity = Genre::fromJsonArray($genreRequestAsArray);

        $this->logger->info("Persisting Genre:", array($genreEntity));

        $this->entityManager->persist($genreEntity);
        $this->entityManager->flush();

        $payload = json_encode($genreEntity, JSON_PRETTY_PRINT);
        $response->getBody()->write($payload);
        return $response->withHeader('Content-Type', 'application/json');
    }

}
