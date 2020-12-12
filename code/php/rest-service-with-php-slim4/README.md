# Introduction
This is a PHP based service to correct OCR words

# Developer stuff
## Installing dependencies with Composer
    
    /kaaj/installs/php/composer/composer.phar install
    /kaaj/installs/php/composer/composer.phar update

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

## Google Authentication

1.  <https://developers.google.com/identity/sign-in/web/backend-auth>

### Unable to read Authorization header in Apache Server
Solution: in .htaccess add the below line:

    SetEnvIf Authorization "(.*)" HTTP_AUTHORIZATION=$1

Reference:
1.  <https://stackoverflow.com/questions/26256730/slimframework-request-headers-don%C2%B4t-read-authorization>
1.  <https://github.com/slimphp/Slim/issues/1616>

## .htaccess Tips
### Echo variables

    ErrorDocument 404 "Request: %{THE_REQUEST} Referrer: %{HTTP_REFERER} Host: %{HTTP_HOST}"
    RewriteRule ^ - [L,R=404]

### Deny access to all

    Deny from all

## Logging with Monolog
Github link: <https://github.com/Seldaek/monolog>

## Sample URLs
### Fetch Pages for a Book

	curl -v -X GET "http://localhost:8000/train/page?bookId=1"

### Ignore words

        curl -v -X PUT "http://localhost:8000/train/word/ignore" -H  "accept: application/json" -H  "Content-Type: application/json" -d '[{"bookId":1,"pageImageId":47,"wordSequenceId":2}]'

### Misc

1.  curl -v -X PUT "http://localhost:8000/train/word" -H  "accept: application/json" -H  "Content-Type: application/json" -d "[{\"correctedText\":\"বিলাপ\",\"ocrWordId\":{\"bookId\":1,\"pageImageId\":505,\"wordSequenceId\":24}},{\"correctedText\":\"গড়াগড়ি\",\"ocrWordId\":{\"bookId\":1,\"pageImageId\":505,\"wordSequenceId\":26}},{\"correctedText\":\"ভিক্ষুকী,\",\"ocrWordId\":{\"bookId\":1,\"pageImageId\":505,\"wordSequenceId\":39}},{\"correctedText\":\"করছিস।\",\"ocrWordId\":{\"bookId\":1,\"pageImageId\":505,\"wordSequenceId\":44}}]"


1.  curl -v -X PUT "http://localhost:8000/train/word" -H  "accept: application/json" -H  "Content-Type: application/json" -d "[{\"correctedText\":\"বিলাপ\",\"ocrWordId\":{\"bookId\":1,\"pageImageId\":505,\"wordSequenceId\":24}}]"


1.  curl -v -X PUT "http://localhost:8000/train/word/ignore" -H  "accept: application/json" -H  "Content-Type: application/json" -d "[{\"bookId\":1,\"pageImageId\":505,\"wordSequenceId\":24}]"


1.  curl -v -X PUT "http://localhost:8000/train/word/ignore" -H  "accept: application/json" -H  "Content-Type: application/json" -d "[{\"bookId\":1,\"pageImageId\":505,\"wordSequenceId\":24}]"

1.  curl -v -X PUT "http://localhost:8000/train/page/complete/40" 

1.  curl -v -X PUT "http://localhost:8000/train/page/ignore/41" 

### URLs on deployed server

1.  curl "http://ocrservice.paawak.me/train/book"

 1.  curl "http://ocrservice.paawak.me/train/page?bookId=1"

1.  curl -X OPTIONS -i  "https://ocrservice.paawak.me"

1.  curl -X GET -i  "https://ocrservice.paawak.me/train/book/1"
