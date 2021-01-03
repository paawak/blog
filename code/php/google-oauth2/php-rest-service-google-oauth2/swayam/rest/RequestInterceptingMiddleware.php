<?php

namespace swayam\rest;

use Psr\Container\ContainerInterface;
use Psr\Log\LoggerInterface;
use Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Http\Server\RequestHandlerInterface as RequestHandler;
use Slim\Psr7\Response;
use Fig\Http\Message\StatusCodeInterface;
use \Slim\Psr7\Headers;

class RequestInterceptingMiddleware {

    const AUTH_HEADER_NAME = 'Authorization';
    const CLIENT_ID = '955630342713-55eu6b3k5hmsg8grojjmk8mj1gi47g37.apps.googleusercontent.com';

    private $container;
    private $logger;

    public function __construct(ContainerInterface $container, LoggerInterface $logger) {
        $this->container = $container;
        $this->logger = $logger;
    }

    public function __invoke(Request $request, RequestHandler $handler): Response {
        $this->logger->info("Handling request of type: " . $request->getMethod() . ", with target-uri: " . $request->getRequestTarget());

        if ($request->getRequestTarget() === '/') {
            return $handler->handle($request);
        }

        if ($request->getMethod() === 'OPTIONS') {
            return $this->addCORSHeaders(new Response());
        }

        if (!$request->hasHeader(self::AUTH_HEADER_NAME)) {
            return $this->getNotAuthorizedResponse();
        }

        $idToken = $request->getHeader(self::AUTH_HEADER_NAME)[0];

        if (!$idToken) {
            return $this->getNotAuthorizedResponse();
        }

        $this->logger->debug('IdToken from OAuth2: ' . $idToken);

        $client = new \Google_Client(['client_id' => self::CLIENT_ID]);
        $payload = $client->verifyIdToken($idToken);
        if ($payload) {
            $this->logger->debug('User authenticated, payload: ', $payload);
        } else {
            return $this->getNotAuthorizedResponse();
        }

        //call the actual handler now
        $response = $handler->handle($request);

        //add CORS before returning
        return $this->addCORSHeaders($response);
    }

    private function addCORSHeaders(Response $response): Response {
        return $response
                        ->withHeader('Access-Control-Allow-Origin', $this->container->get('cors.allow-origin'))
                        ->withHeader('Access-Control-Allow-Headers', 'X-Requested-With, Content-Type, Accept, Origin, Authorization')
                        ->withHeader('Access-Control-Allow-Credentials', 'true')
                        ->withHeader('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, PATCH, OPTIONS');
    }

    private function getNotAuthorizedResponse(): Response {
        $reasonPhrase = 'Not authorised: could not authenticate user';
        $this->logger->warning($reasonPhrase);
        $headers = new Headers();
        $headers->addHeader('Content-Type', 'application/json');
        $response = new Response(StatusCodeInterface::STATUS_UNAUTHORIZED, $headers);

        $jsonPayload = json_encode(['httpStatusCode' => StatusCodeInterface::STATUS_UNAUTHORIZED, 'error' => $reasonPhrase], JSON_PRETTY_PRINT);
        $response->getBody()->write($jsonPayload);
        return $response;
    }

}
