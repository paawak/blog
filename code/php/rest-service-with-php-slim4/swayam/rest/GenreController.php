<?php

namespace swayam\rest;

use \Exception as Exception;
use Psr\Http\Message\ResponseInterface as Response;
use Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Log\LoggerInterface;
use swayam\model\Genre;
use swayam\repo\GenreRepository;

require_once __DIR__ . '/../model/Genre.php';

class GenreController {

    private $genreRepository;
    private $logger;

    public function __construct(GenreRepository $genreRepository, LoggerInterface $logger) {
        $this->genreRepository = $genreRepository;
        $this->logger = $logger;
    }

    public function getAllGenres(Request $request, Response $response) {
        $genres = $this->genreRepository->getAllGenres();
        $genresAsJson = json_encode($genres, JSON_PRETTY_PRINT);
        $response->getBody()->write($genresAsJson);
        return $response->withHeader('Content-Type', 'application/json');
    }

    public function getGenreById(Request $request, Response $response, $genreId) {
        $genre = $this->genreRepository->getGenreById($genreId);
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

        $savedGenre = $this->genreRepository->addNewGenre($genreEntity);

        $payload = json_encode($savedGenre, JSON_PRETTY_PRINT);
        $response->getBody()->write($payload);
        return $response->withHeader('Content-Type', 'application/json');
    }

}
