<?php

use Doctrine\ORM\EntityManager;
use Doctrine\ORM\Tools\Setup;
use Monolog\Handler\RotatingFileHandler;
use Monolog\Handler\StreamHandler;
use Monolog\Logger;
use Monolog\Formatter\LineFormatter;
use Monolog\Processor\IntrospectionProcessor;
use Monolog\ErrorHandler;
use Psr\Log\LoggerInterface;
use Psr\Container\ContainerInterface;
use swayam\repo\AuthorRepository;
use swayam\repo\AuthorRepositoryImpl;
use swayam\repo\GenreRepository;
use swayam\repo\GenreRepositoryImpl;
use swayam\repo\BookRepository;
use swayam\repo\BookRepositoryImpl;

require_once __DIR__ . '/../repo/AuthorRepositoryImpl.php';
require_once __DIR__ . '/../repo/GenreRepositoryImpl.php';
require_once __DIR__ . '/../repo/BookRepositoryImpl.php';

return [
    LoggerInterface::class => function (ContainerInterface $container) {
        $dateFormat = "Y-m-d\TH:i:sP";
        $output = "[%datetime%] %level_name%: %message% %context% %extra%\n";
        $formatter = new LineFormatter($output, $dateFormat);

        $logger = new Logger('ocr-correction-rest');
        $fileHandler = new RotatingFileHandler($container->get('logger.fileName'), $container->get('logger.maxFiles'));
        $fileHandler->setFormatter($formatter);
        $logger->pushHandler($fileHandler);

        if ($container->get('logger.console') === true) {
            $consoleHandler = new StreamHandler('php://stdout', Logger::DEBUG);
            $consoleHandler->setFormatter($formatter);
            $logger->pushHandler($consoleHandler);
        }

        $logger->pushProcessor(new IntrospectionProcessor());

        ErrorHandler::register($logger);

        return $logger;
    },
    EntityManager::class => function (ContainerInterface $container) {
        $dbParams = array(
            'driver' => $container->get('database.driver'),
            'user' => $container->get('database.user'),
            'password' => $container->get('database.password'),
            'dbname' => $container->get('database.name'),
            'host' => $container->get('database.host'),
            'charset' => 'UTF8'
        );

        $isDevMode = true;
        $proxyDir = null;
        $cache = null;
        $useSimpleAnnotationReader = false;
        $config = Setup::createAnnotationMetadataConfiguration(array(__DIR__ . "/../model"), $isDevMode, $proxyDir, $cache, $useSimpleAnnotationReader);

        $entityManager = EntityManager::create($dbParams, $config);
        return $entityManager;
    },
    AuthorRepository::class => function (EntityManager $entityManager) {
        return new AuthorRepositoryImpl($entityManager);
    },
    GenreRepository::class => function (EntityManager $entityManager) {
        return new GenreRepositoryImpl($entityManager);
    },
    BookRepository::class => function (EntityManager $entityManager, AuthorRepository $authorRepository, GenreRepository $genreRepository) {
        return new BookRepositoryImpl($entityManager, $authorRepository, $genreRepository);
    }
];
