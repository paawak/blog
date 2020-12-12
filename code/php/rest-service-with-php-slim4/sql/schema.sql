-- database: one_to_many_example

DROP TABLE IF EXISTS genre;

DROP TABLE IF EXISTS book;

DROP TABLE IF EXISTS author;

CREATE TABLE genre (
	id BIGINT(20) NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT,
	short_name VARCHAR(10) NOT NULL,
	name VARCHAR(100) NOT NULL
);


CREATE TABLE author (
	id BIGINT(20) NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT,
	first_name VARCHAR(200) NOT NULL,
	last_name VARCHAR(200) NOT NULL
);


CREATE TABLE book (
	id BIGINT(20) NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT,
	title TEXT NOT NULL,
	author_id BIGINT(20) NOT NULL,
	genre_id BIGINT(20) NOT NULL,
	FOREIGN KEY (author_id) REFERENCES author(id),
	FOREIGN KEY (genre_id) REFERENCES genre(id)
);