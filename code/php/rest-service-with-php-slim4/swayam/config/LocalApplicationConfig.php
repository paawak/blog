<?php

return [
    'database.host' => 'localhost',
    'database.user' => 'root',
    'database.password' => 'root123',
    'database.name' => 'porua',
    'database.driver' => 'pdo_mysql',
    'cors.allow-origin' => 'http://localhost:3000',
    'ocr.imageStore' => '/kaaj/source/porua/tesseract-ocr-rest/images/',
    'logger.fileName' => '/kaaj/source/porua-ocr/logs/ocr-correction-rest.log',
    'logger.maxFiles' => 10,
    'logger.console' => true
];
