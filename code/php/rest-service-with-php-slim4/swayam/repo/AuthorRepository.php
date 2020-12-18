<?php

namespace swayam\repo;

use swayam\model\Author;

/**
 *
 * @author paawak
 */
interface AuthorRepository {

    public function getAllAuthors();

    public function getAuthorById(int $authorId): Author;

    public function searchAuthorsByCountry(string $country): Array;

    public function addNewAuthor(Author $author): Author;
    
}
