<?php

namespace swayam\repo;

use Doctrine\ORM\EntityManager;
use swayam\model\Author;

require_once __DIR__ . '/AuthorRepository.php';

/**
 *
 * @author paawak
 */
class AuthorRepositoryImpl implements AuthorRepository {

    private $entityManager;

    public function __construct(EntityManager $entityManager) {
        $this->entityManager = $entityManager;
    }

    public function addNewAuthor(Author $author): Author {
        $this->entityManager->persist($author);
        $this->entityManager->flush();
        return $author;
    }

    public function getAllAuthors(): Array {
        return $this->entityManager->getRepository(Author::class)->findAll();
    }

    public function getAuthorById(int $authorId): Author {
        return $this->entityManager->getRepository(Author::class)->find($authorId);
    }

    public function searchAuthorsByCountry(string $country): array {
        return $this->entityManager->getRepository(Author::class)->findBy(
                        array(
                            'address.country' => $country
                        ),
                        array('id' => 'DESC')
        );
    }

}
