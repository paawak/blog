<?php

require __DIR__ . '/swayam/rest/IndexController.php';
require __DIR__ . '/swayam/rest/AuthorController.php';
require __DIR__ . '/swayam/rest/RequestInterceptingMiddleware.php';

use DI\Bridge\Slim\Bridge;
use Slim\Handlers\ErrorHandler;
use Slim\Factory\ServerRequestCreatorFactory;
use Psr\Log\LoggerInterface;
use swayam\rest\IndexController;
use swayam\rest\AuthorController;
use swayam\rest\RequestInterceptingMiddleware;

$container = require __DIR__ . '/swayam/config/DIContainerBootstrap.php';

$app = Bridge::create($container);

$logger = $container->get(LoggerInterface::class);

$callableResolver = $app->getCallableResolver();
$responseFactory = $app->getResponseFactory();
$serverRequestCreator = ServerRequestCreatorFactory::create();
$request = $serverRequestCreator->createServerRequestFromGlobals();
$errorHandler = new ErrorHandler($callableResolver, $responseFactory, $logger);

$app->addRoutingMiddleware();
$app->addBodyParsingMiddleware();
$errorMiddleware = $app->addErrorMiddleware(true, true, true);
$errorMiddleware->setDefaultErrorHandler($errorHandler);

$app->options('/{routes:.+}', function ($request, $response, $args) {
    return $response;
});

$app->add($container->get(RequestInterceptingMiddleware::class));

$app->get('/', [IndexController::class, 'get']);

$app->get('/author', [AuthorController::class, 'getAllAuthors']);
$app->post('/author', [AuthorController::class, 'addNewAuthor']);

$app->run();
?>
