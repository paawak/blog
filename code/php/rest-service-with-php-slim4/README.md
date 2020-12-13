# Introduction
Example of a REST Service with PHP Slim4

# Developer stuff
## Installing dependencies with Composer
    
    composer.phar install
    composer.phar update

## REST Library Slim
1.  <http://www.slimframework.com/docs/v4/>
1.  <http://www.slimframework.com/docs/v4/objects/request.html>

## Dependency Injection with PHP-DI

    composer require php-di/slim-bridge

1.  <https://php-di.org/doc/frameworks/slim.html>
1.  <https://php-di.org/doc/container-configuration.html>
1.  Demo: <https://github.com/PHP-DI/demo>

## ORM Library Doctrine
1.  Getting Started: <https://www.doctrine-project.org/projects/doctrine-orm/en/2.7/tutorials/getting-started.html#getting-started-with-doctrine>
1.  Configuration: <https://www.doctrine-project.org/projects/doctrine-orm/en/2.7/reference/configuration.html>
1.  Repository Pattern: <https://www.doctrine-project.org/projects/doctrine-orm/en/2.7/tutorials/getting-started.html#entity-repositories>

### Update Queries
1.  <https://www.doctrine-project.org/projects/doctrine-orm/en/2.7/reference/dql-doctrine-query-language.html#update-queries>
1.  <https://www.doctrine-project.org/projects/doctrine-orm/en/2.7/tutorials/getting-started.html#updating-entities>

# Rest API
## Author

        curl -v -X POST "http://localhost:8000/author" -H  "accept: application/json" -H  "Content-Type: application/json" -d @test/add_new_author.json


