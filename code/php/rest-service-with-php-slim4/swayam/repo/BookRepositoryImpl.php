<?php

namespace swayam\repo;

use Doctrine\ORM\EntityManager;
use swayam\model\Book;
use swayam\repo\AuthorRepository;
use swayam\repo\GenreRepository;

require_once __DIR__ . '/BookRepository.php';

/**
 *
 * @author paawak
 */
class BookRepositoryImpl implements BookRepository {

    private EntityManager $entityManager;
    private AuthorRepository $authorRepository;
    private GenreRepository $genreRepository;

    public function __construct(EntityManager $entityManager, AuthorRepository $authorRepository, GenreRepository $genreRepository) {
        $this->entityManager = $entityManager;
        $this->authorRepository = $authorRepository;
        $this->genreRepository = $genreRepository;
    }

    public function addNewBook(Book $book): Book {   
        $authorId = $book->getAuthor()->getId();
        $authorEntity = $this->authorRepository->getAuthorById($authorId);
        if ($authorEntity == null) {
            throw new Exception("No author found for the id" . $authorId);
        }
        
        $genreId = $book->getGenre()->getId();
        $genreEntity = $this->genreRepository->getGenreById($genreId);
        if ($genreEntity == null) {
            throw new Exception("No genre found for the id" . $genreId);
        }
        
        $book->setAuthor($authorEntity);
        $book->setGenre($genreEntity);
        
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
