<?php

namespace com\swayam\ocr\porua\model;

use Doctrine\ORM\Mapping\Embeddable;
use Doctrine\ORM\Mapping\Column;
use Doctrine\ORM\Mapping\Id;

/** @Embeddable */
class OcrWordId implements \JsonSerializable {

    /**
     * @Id
     * @Column(name = "book_id", type="integer") 
     */
    private $bookId;

    /**
     * @Id
     * @Column(name = "page_image_id", type="integer") 
     */
    private $pageImageId;

    /**
     * @Id
     * @Column(name = "word_sequence_id", type="integer") 
     */
    private $wordSequenceId;

    public function getBookId() {
        return $this->bookId;
    }

    public function getPageImageId() {
        return $this->pageImageId;
    }

    public function getWordSequenceId() {
        return $this->wordSequenceId;
    }

    public function setBookId($bookId): void {
        $this->bookId = $bookId;
    }

    public function setPageImageId($pageImageId): void {
        $this->pageImageId = $pageImageId;
    }

    public function setWordSequenceId($wordSequenceId): void {
        $this->wordSequenceId = $wordSequenceId;
    }

    public function jsonSerialize() {
        return get_object_vars($this);
    }

    public static function fromJsonArray($ocrWordIdAsArray) {
        $ocrWordId = new OcrWordId();
        foreach ($ocrWordIdAsArray as $fieldName => $value) {
            $ocrWordId->{$fieldName} = $value;
        }
        return $ocrWordId;
    }

}
