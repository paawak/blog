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
### Add new Author

        curl -v -X POST "http://localhost:8000/author" -H  "accept: application/json" -H  "Content-Type: application/json" -H "Authorization: MySecretToken" -d @test/add_new_author.json

### Get all Authors

        curl -v -H "Authorization: MySecretToken" "http://localhost:8000/author" 

### Get Author by ID

        curl -v -H "Authorization: MySecretToken" "http://localhost:8000/author/1" 

### Search Author by Country

        curl -v -H "Authorization: MySecretToken" "http://localhost:8000/author/search?country=India" 

## Genre
### Add new Genre

        curl -v -X POST "http://localhost:8000/genre" -H "Authorization: MySecretToken" -H  "accept: application/json" -H  "Content-Type: application/json" -d @test/add_new_genre.json

### Get all Genres

        curl -v -H "Authorization: MySecretToken" "http://localhost:8000/genre" 

### Get Genre by ID

        curl -v -H "Authorization: MySecretToken" "http://localhost:8000/genre/1" 

## Book
### Add new Book

        curl -v -X POST "http://localhost:8000/book" -H "Authorization: MySecretToken" -H  "accept: application/json" -H  "Content-Type: application/json" -d @test/add_new_book.json

### Get all Books

        curl -v -H "Authorization: MySecretToken" "http://localhost:8000/book" 

### Get Book by ID

        curl -v -H "Authorization: MySecretToken" "http://localhost:8000/book/1" 
