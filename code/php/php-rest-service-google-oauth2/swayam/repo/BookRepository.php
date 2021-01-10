<?php

namespace swayam\repo;

use swayam\model\Book;

/**
 *
 * @author paawak
 */
interface BookRepository {

    public function getAllBooks(): Array;

    public function getBookById(int $bookId): Book;

    public function addNewBook(Book $book): Book;
    
}
