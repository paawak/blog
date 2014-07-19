package com.swayam.demo.jasper;

import java.io.Serializable;

public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String name;

    private final String author;

    private final String publisher;

    private final Language language;

    public Book(String name, String author, String publisher, Language language) {
	this.name = name;
	this.author = author;
	this.publisher = publisher;
	this.language = language;
    }

    public String getName() {
	return name;
    }

    public String getAuthor() {
	return author;
    }

    public String getPublisher() {
	return publisher;
    }

    public Language getLanguage() {
	return language;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((author == null) ? 0 : author.hashCode());
	result = prime * result + ((language == null) ? 0 : language.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((publisher == null) ? 0 : publisher.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Book other = (Book) obj;
	if (author == null) {
	    if (other.author != null)
		return false;
	} else if (!author.equals(other.author))
	    return false;
	if (language == null) {
	    if (other.language != null)
		return false;
	} else if (!language.equals(other.language))
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (publisher == null) {
	    if (other.publisher != null)
		return false;
	} else if (!publisher.equals(other.publisher))
	    return false;
	return true;
    }

}
