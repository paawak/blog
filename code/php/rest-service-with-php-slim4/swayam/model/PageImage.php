<?php

namespace com\swayam\ocr\porua\model;

use Doctrine\ORM\Mapping\Entity;
use Doctrine\ORM\Mapping\Table;
use Doctrine\ORM\Mapping\Id;
use Doctrine\ORM\Mapping\OneToOne;
use Doctrine\ORM\Mapping\JoinColumn;
use Doctrine\ORM\Mapping\GeneratedValue;
use Doctrine\ORM\Mapping\Column;

use com\swayam\ocr\porua\model\Book;

/**
 * @Entity
 * @Table(name="page_image")
 */
class PageImage implements \JsonSerializable {

    /**
     * @Id
     * @Column(type="integer")
     * @GeneratedValue(strategy="IDENTITY")
     */
    private $id;

    /**
     * @OneToOne(targetEntity="Book")
     * @JoinColumn(name="book_id", referencedColumnName="id")
     */
    private $book;

    /** @Column */
    private $name;

    /** @Column(name = "page_number", type="integer") */
    private $pageNumber;

    /** @Column(type="boolean") */
    private $ignored;

    /** @Column(name = "correction_completed", type="boolean") */
    private $correctionCompleted;

    public function getId() {
        return $this->id;
    }

    public function getBook() {
        return $this->book;
    }

    public function getName() {
        return $this->name;
    }

    public function getPageNumber() {
        return $this->pageNumber;
    }

    public function getIgnored() {
        return $this->ignored;
    }

    public function getCorrectionCompleted() {
        return $this->correctionCompleted;
    }

    public function setId($id): void {
        $this->id = $id;
    }

    public function setBook($book): void {
        $this->book = $book;
    }

    public function setName($name): void {
        $this->name = $name;
    }

    public function setPageNumber($pageNumber): void {
        $this->pageNumber = $pageNumber;
    }

    public function setIgnored($ignored): void {
        $this->ignored = $ignored;
    }

    public function setCorrectionCompleted($correctionCompleted): void {
        $this->correctionCompleted = $correctionCompleted;
    }
    
    public function jsonSerialize() {
        return get_object_vars($this);
    }

}
