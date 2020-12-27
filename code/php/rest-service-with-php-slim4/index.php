<?php

require __DIR__ . '/swayam/rest/IndexController.php';
require __DIR__ . '/swayam/rest/AuthorController.php';
require __DIR__ . '/swayam/rest/GenreController.php';
require __DIR__ . '/swayam/rest/BookController.php';
require __DIR__ . '/swayam/rest/RequestInterceptingMiddleware.php';

use DI\Bridge\Slim\Bridge;
use Slim\Handlers\ErrorHandler;
use Psr\Log\LoggerInterface;
use swayam\rest\IndexController;
use swayam\rest\AuthorController;
use swayam\rest\GenreController;
use swayam\rest\BookController;
use swayam\rest\RequestInterceptingMiddleware;

$container = require __DIR__ . '/swayam/config/DIContainerBootstrap.php';
$app = Bridge::create($container);

$callableResolver = $app->getCallableResolver();
$responseFactory = $app->getResponseFactory();
$logger = $container->get(LoggerInterface::class);
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

$app->get('/author/search', [AuthorController::class, 'searchAuthorsByCountry']);
$app->get('/author', [AuthorController::class, 'getAllAuthors']);
$app->get('/author/{authorId}', [AuthorController::class, 'getAuthorById']);
$app->post('/author', [AuthorController::class, 'addNewAuthor']);

$app->get('/genre', [GenreController::class, 'getAllGenres']);
$app->get('/genre/{genreId}', [GenreController::class, 'getGenreById']);
$app->post('/genre', [GenreController::class, 'addNewGenre']);

$app->get('/book', [BookController::class, 'getAllBooks']);
$app->get('/book/{bookId}', [BookController::class, 'getBookById']);
$app->post('/book', [BookController::class, 'addNewBook']);

$app->run();
?>
