<?php

namespace swayam\model;

use Doctrine\ORM\Mapping\Entity;
use Doctrine\ORM\Mapping\Table;
use Doctrine\ORM\Mapping\Id;
use Doctrine\ORM\Mapping\GeneratedValue;
use Doctrine\ORM\Mapping\Column;

/**
 * @Entity
 * @Table(name="genre")
 */
class Genre implements \JsonSerializable {

    /**
     * @Id
     * @Column(type="integer")
     * @GeneratedValue(strategy="IDENTITY")
     */
    private $id;

    /** @Column(type="string") */
    private $name;

    /** @Column(name="short_name", type="string") */
    private $shortName;

    public function getId() {
        return $this->id;
    }

    public function getName() {
        return $this->name;
    }

    public function getShortName() {
        return $this->shortName;
    }

    public function setId($id): void {
        $this->id = $id;
    }

    public function setName($name): void {
        $this->name = $name;
    }

    public function setShortName($shortName): void {
        $this->shortName = $shortName;
    }

    public function jsonSerialize() {
        return get_object_vars($this);
    }
    
    public static function fromJsonArray($genreAsArray) {
        $genre = new Genre();
        foreach ($genreAsArray as $fieldName => $value) {
            $genre->{$fieldName} = $value;
        }
        return $genre;
    }

}
