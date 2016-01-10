package com.swayam.demo.json.simple;

import java.time.LocalDate;

public class Person {

    private final long id;
    private final String firstName;
    private final String lastName;
    private final LocalDate dateOfBirth;

    public Person(long id, String firstName, String lastName, LocalDate dateOfBirth) {
	this.id = id;
	this.firstName = firstName;
	this.lastName = lastName;
	this.dateOfBirth = dateOfBirth;
    }

    /** The default constructor is needed for Json to Java conversion */
    public Person() {
	this(0, null, null, null);
    }

    public long getId() {
	return id;
    }

    public String getFirstName() {
	return firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public LocalDate getDateOfBirth() {
	return dateOfBirth;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
	result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
	result = prime * result + (int) (id ^ (id >>> 32));
	result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
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
	Person other = (Person) obj;
	if (dateOfBirth == null) {
	    if (other.dateOfBirth != null)
		return false;
	} else if (!dateOfBirth.equals(other.dateOfBirth))
	    return false;
	if (firstName == null) {
	    if (other.firstName != null)
		return false;
	} else if (!firstName.equals(other.firstName))
	    return false;
	if (id != other.id)
	    return false;
	if (lastName == null) {
	    if (other.lastName != null)
		return false;
	} else if (!lastName.equals(other.lastName))
	    return false;
	return true;
    }

}
