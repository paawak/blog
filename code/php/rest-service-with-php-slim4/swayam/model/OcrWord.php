<?php

namespace com\swayam\ocr\porua\model;

use Doctrine\ORM\Mapping\Entity;
use Doctrine\ORM\Mapping\Embedded;
use Doctrine\ORM\Mapping\Table;
use Doctrine\ORM\Mapping\Column;

/**
 * @Entity
 * @Table(name="ocr_word")
 */
class OcrWord implements \JsonSerializable {

    /**
     * @Embedded(class = "OcrWordId", columnPrefix=false) 
     */
    private $ocrWordId;

    /** @Column(name = "raw_text") */
    private $rawText;

    /** @Column(name = "corrected_text") */
    private $correctedText;

    /** @Column(type="integer") */
    private $x1;

    /** @Column(type="integer") */
    private $y1;

    /** @Column(type="integer") */
    private $x2;

    /** @Column(type="integer") */
    private $y2;

    /** @Column(type="float") */
    private $confidence;

    /** @Column(name = "line_number", type="integer") */
    private $lineNumber;

    /** @Column(type="boolean") */
    private $ignored;

    public function getOcrWordId() {
        return $this->ocrWordId;
    }

    public function getRawText() {
        return $this->rawText;
    }

    public function getCorrectedText() {
        return $this->correctedText;
    }

    public function getX1() {
        return $this->x1;
    }

    public function getY1() {
        return $this->y1;
    }

    public function getX2() {
        return $this->x2;
    }

    public function getY2() {
        return $this->y2;
    }

    public function getConfidence() {
        return $this->confidence;
    }

    public function getLineNumber() {
        return $this->lineNumber;
    }

    public function getIgnored() {
        return $this->ignored;
    }

    public function setOcrWordId($ocrWordId): void {
        $this->ocrWordId = $ocrWordId;
    }

    public function setRawText($rawText): void {
        $this->rawText = $rawText;
    }

    public function setCorrectedText($correctedText): void {
        $this->correctedText = $correctedText;
    }

    public function setX1($x1): void {
        $this->x1 = $x1;
    }

    public function setY1($y1): void {
        $this->y1 = $y1;
    }

    public function setX2($x2): void {
        $this->x2 = $x2;
    }

    public function setY2($y2): void {
        $this->y2 = $y2;
    }

    public function setConfidence($confidence): void {
        $this->confidence = $confidence;
    }

    public function setLineNumber($lineNumber): void {
        $this->lineNumber = $lineNumber;
    }

    public function setIgnored($ignored): void {
        $this->ignored = $ignored;
    }

    public function jsonSerialize() {
        return get_object_vars($this);
    }

}
