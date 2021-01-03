<?php

namespace swayam\model;

use Doctrine\ORM\Mapping\Entity;
use Doctrine\ORM\Mapping\Table;
use Doctrine\ORM\Mapping\Id;
use Doctrine\ORM\Mapping\GeneratedValue;
use Doctrine\ORM\Mapping\Column;
use Doctrine\ORM\Mapping\OneToOne;
use Doctrine\ORM\Mapping\JoinColumn;

/**
 * @Entity
 * @Table(name="book")
 */
class Book implements \JsonSerializable {

    /**
     * @Id
     * @Column(type="integer")
     * @GeneratedValue(strategy="IDENTITY")
     */
    private $id;

    /** @Column(type="string") */
    private $title;

    /**
     * @OneToOne(targetEntity="Author")
     * @JoinColumn(name="author_id", referencedColumnName="id")
     */
    private $author;

    /**
     * @OneToOne(targetEntity="Genre")
     * @JoinColumn(name="genre_id", referencedColumnName="id")
     */
    private $genre;

    public function getId() {
        return $this->id;
    }

    public function getTitle() {
        return $this->title;
    }

    public function getAuthor() {
        return $this->author;
    }

    public function getGenre() {
        return $this->genre;
    }

    public function setId($id): void {
        $this->id = $id;
    }

    public function setTitle($title): void {
        $this->title = $title;
    }

    public function setAuthor($author): void {
        $this->author = $author;
    }

    public function setGenre($genre): void {
        $this->genre = $genre;
    }

    public function jsonSerialize() {
        return get_object_vars($this);
    }

    public static function fromJsonArray($bookAsArray) {
        $book = new Book();
        foreach ($bookAsArray as $fieldName => $value) {
            if ($fieldName === 'author') {
                $book->author = Author::fromJsonArray($value);
            } else if ($fieldName === 'genre') {
                $book->genre = Genre::fromJsonArray($value);
            } else {
                $book->{$fieldName} = $value;
            }
        }
        return $book;
    }

}
