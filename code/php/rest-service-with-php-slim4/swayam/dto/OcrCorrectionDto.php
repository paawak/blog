<?php

namespace com\swayam\ocr\porua\dto;

require_once __DIR__ . '/../dto/OcrCorrectionDto.php';

use com\swayam\ocr\porua\model\OcrWordId;

class OcrCorrectionDto {

    private $ocrWordId;
    private $correctedText;

    public function getOcrWordId() {
        return $this->ocrWordId;
    }

    public function getCorrectedText() {
        return $this->correctedText;
    }

    public function setOcrWordId($ocrWordId): void {
        $this->ocrWordId = $ocrWordId;
    }

    public function setCorrectedText($correctedText): void {
        $this->correctedText = $correctedText;
    }

    public static function fromJsonArray($ocrCorrectionDtoAsArray) {
        $ocrCorrectionDto = new OcrCorrectionDto();
        $ocrCorrectionDto->ocrWordId = OcrWordId::fromJsonArray($ocrCorrectionDtoAsArray['ocrWordId']);
        $ocrCorrectionDto->correctedText = $ocrCorrectionDtoAsArray['correctedText'];
        return $ocrCorrectionDto;
    }

}
