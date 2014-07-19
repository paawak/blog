package com.swayam.demo.jasper;

import java.util.Arrays;
import java.util.List;

public class DummyBookServiceImpl implements BookService {

    @Override
    public List<Book> getBooksByLanguage(Language language) {
	switch (language) {

	case BANGLA:
	    return Arrays.asList(
		    new Book("Khudhito Pashan", "Robindro Nath Thakur", "-", Language.BANGLA),
		    new Book("Choritrohin", "Sharat", "Ananda", Language.BANGLA),
		    new Book("Hate Bajare", "Bonoful", "Ananda", Language.BANGLA),
		    new Book("Jhinder Bondi", "Sharadindu", "Ananda", Language.BANGLA),
		    new Book("Jabali", "Parashuram", "-", Language.BANGLA),
		    new Book("Lota Kombol", "Sanjib Chattapadhyay", "Ananda", Language.BANGLA)
		    );

	case ENGLISH:
	    return Arrays.asList(
		    new Book("Dead Souls", "Nikolai Gogol", "Raduga", Language.ENGLISH),
		    new Book("Crime & Punishment", "Froydor Dostovoesky", "-", Language.ENGLISH),
		    new Book("Moti Guj The Rebel Elephant", "Kipling", "Worldpress", Language.ENGLISH),
		    new Book("The Brazilian Cat", "Arthur Conan Doyle", "Oxford", Language.ENGLISH),
		    new Book("The Midnight's Children", "Salman Rushdie", "-", Language.ENGLISH),
		    new Book("The Dogs Of War", "Forsyth", "-", Language.ENGLISH)
		    );

	default:
	    throw new UnsupportedOperationException();
	}

    }
}
