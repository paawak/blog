<?php

namespace swayam\repo;

use swayam\model\Genre;

/**
 *
 * @author paawak
 */
interface GenreRepository {

    public function getAllGenres(): Array;

    public function getGenreById(int $genreId): Genre;

    public function addNewGenre(Genre $genre): Genre;
    
}
