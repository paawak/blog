package com.swayam.demo.sort;

import java.util.Comparator;

public class PersonComparatorTransitive implements Comparator<Person> {

    @Override
    public int compare(Person person1, Person person2) {
	if ((person1.getId() == null) && (person2.getId() != null)) {
	    return -1;
	}

	if ((person1.getId() != null) && (person2.getId() == null)) {
	    return 1;
	}

	if ((person1.getId() == null) && (person2.getId() == null)) {
	    return 0;
	}

	return person1.getId() - person2.getId();

    }

}
