<?php

namespace swayam\repo;

use Doctrine\ORM\EntityManager;
use swayam\model\Book;

require_once __DIR__ . '/BookRepository.php';

/**
 *
 * @author paawak
 */
class BookRepositoryImpl implements BookRepository {

    private $entityManager;

    public function __construct(EntityManager $entityManager) {
        $this->entityManager = $entityManager;
    }

    public function addNewBook(Book $book): Book {
        $this->entityManager->persist($book);
        $this->entityManager->flush();
        return $book;
    }

    public function getAllBooks(): Array {
        return $this->entityManager->getRepository(Book::class)->findAll();
    }

    public function getBookById(int $bookId): Book {
        return $this->entityManager->getRepository(Book::class)->find($bookId);
    }

}
