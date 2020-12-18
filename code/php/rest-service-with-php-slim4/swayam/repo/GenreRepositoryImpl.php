<?php

namespace swayam\repo;

use Doctrine\ORM\EntityManager;
use swayam\model\Genre;

require_once __DIR__ . '/GenreRepository.php';

/**
 *
 * @author paawak
 */
class GenreRepositoryImpl implements GenreRepository {

    private $entityManager;

    public function __construct(EntityManager $entityManager) {
        $this->entityManager = $entityManager;
    }

    public function addNewGenre(Genre $genre): Genre {
        $this->entityManager->persist($genre);
        $this->entityManager->flush();
        return $genre;
    }

    public function getAllGenres(): Array {
        return $this->entityManager->getRepository(Genre::class)->findAll();
    }

    public function getGenreById(int $genreId): Genre {
        return $this->entityManager->getRepository(Genre::class)->find($genreId);
    }

}
