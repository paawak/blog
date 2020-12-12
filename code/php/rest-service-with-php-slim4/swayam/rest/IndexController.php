<?php

namespace com\swayam\ocr\porua\rest;

use Psr\Http\Message\ResponseInterface as Response;
use Psr\Http\Message\ServerRequestInterface as Request;

class IndexController {

    public function get(Request $request, Response $response) {
        $payload = json_encode(['health' => 'OK'], JSON_PRETTY_PRINT);
        $response->getBody()->write($payload);
        return $response->withHeader('Content-Type', 'application/json');
    }

}
