<?php

namespace swayam\model;

use Doctrine\ORM\Mapping\Entity;
use Doctrine\ORM\Mapping\Embedded;
use Doctrine\ORM\Mapping\Table;
use Doctrine\ORM\Mapping\Id;
use Doctrine\ORM\Mapping\GeneratedValue;
use Doctrine\ORM\Mapping\Column;

/**
 * @Entity
 * @Table(name="book")
 */
class Author implements \JsonSerializable {

    /**
     * @Id
     * @Column(type="integer")
     * @GeneratedValue(strategy="IDENTITY")
     */
    private $id;

    /** @Column(name="first_name", type="string") */
    private $firstName;

    /** @Column(name="last_name", type="string") */
    private $lastName;

    /**
     * @Embedded(class = "Address", columnPrefix=false) 
     */
    private $address;

    public function getId() {
        return $this->id;
    }

    public function getFirstName() {
        return $this->firstName;
    }

    public function getLastName() {
        return $this->lastName;
    }

    public function getAddress() {
        return $this->address;
    }

    public function setId($id): void {
        $this->id = $id;
    }

    public function setFirstName($firstName): void {
        $this->firstName = $firstName;
    }

    public function setLastName($lastName): void {
        $this->lastName = $lastName;
    }

    public function setAddress($address): void {
        $this->address = $address;
    }

    public function jsonSerialize() {
        return get_object_vars($this);
    }

}
