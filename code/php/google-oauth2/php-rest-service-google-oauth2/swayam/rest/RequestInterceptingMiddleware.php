<?php

namespace swayam\rest;

use Psr\Container\ContainerInterface;
use Psr\Log\LoggerInterface;
use Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Http\Server\RequestHandlerInterface as RequestHandler;
use Slim\Psr7\Response;

class RequestInterceptingMiddleware {

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

}
