DROP TABLE IF EXISTS person;

CREATE TABLE person (
	id INTEGER(6) NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT,
	first_name VARCHAR(50) NOT NULL,
	last_name VARCHAR(50) NOT NULL,
	date_of_birth DATE NOT NULL
);

DROP TABLE IF EXISTS pet;

CREATE TABLE pet (
	id INTEGER(6) NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	animal_type VARCHAR(50) NOT NULL,
	date_of_birth DATE NOT NULL
);

insert into pet (name, animal_type, date_of_birth) values ('Benjamin', 'BUNNY', '2008/09/09');
