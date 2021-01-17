# Introduction
Example of how to secure REST Service with PHP Slim4, with Google authentication

# Developer stuff
## Google Authentication

1.  <https://developers.google.com/identity/sign-in/web/backend-auth>

### Unable to read Authorization header in Apache Server
Solution: in .htaccess add the below line:

    SetEnvIf Authorization "(.*)" HTTP_AUTHORIZATION=$1

Reference:
1.  <https://stackoverflow.com/questions/26256730/slimframework-request-headers-don%C2%B4t-read-authorization>
1.  <https://github.com/slimphp/Slim/issues/1616>

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
