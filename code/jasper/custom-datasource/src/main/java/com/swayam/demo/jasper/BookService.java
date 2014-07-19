package com.swayam.demo.jasper;

import java.util.List;

public interface BookService {

    List<Book> getBooksByLanguage(Language language);

}
