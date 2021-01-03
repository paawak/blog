<?php

/**
 * The bootstrap file creates and returns the container.
 */
use DI\ContainerBuilder;

require __DIR__ . '/../../vendor/autoload.php';

$containerBuilder = new ContainerBuilder;
$containerBuilder->addDefinitions(__DIR__ . '/DIConfiguration.php');

$containerBuilder->addDefinitions(__DIR__ . '/LocalApplicationConfig.php');

$container = $containerBuilder->build();

return $container;
