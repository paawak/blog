package com.swayam.demo.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PersonComparatorGoodTest {

    private final List<Person> persons = new ArrayList<>();

    @Test
    public void testCompare() {
	Collections.sort(persons, new PersonComparatorGood());
	printPersons();
    }

    private void printPersons() {
	persons.forEach((Person person) -> {
	    System.out.println(person.getId());
	});
    }

    @Before
    public void setup() {
	persons.add(new Person("TestName", 3));
	persons.add(new Person("TestName", 290));
	persons.add(new Person("TestName", 1));
	persons.add(new Person("TestName", null));
	persons.add(new Person("TestName", null));
	persons.add(new Person("TestName", null));
	persons.add(new Person("TestName", null));
	persons.add(new Person("TestName", null));
	persons.add(new Person("TestName", null));
	persons.add(new Person("TestName", null));
	persons.add(new Person("TestName", null));
	persons.add(new Person("TestName", null));
	persons.add(new Person("TestName", null));
	persons.add(new Person("TestName", null));
	persons.add(new Person("TestName", null));
	persons.add(new Person("TestName", null));
	persons.add(new Person("TestName", null));
	persons.add(new Person("TestName", null));
	persons.add(new Person("TestName", null));
	persons.add(new Person("TestName", null));
	persons.add(new Person("TestName", null));
	persons.add(new Person("TestName", null));
	persons.add(new Person("TestName", 1));
	persons.add(new Person("TestName", 398));
	persons.add(new Person("TestName", 46));
	persons.add(new Person("TestName", 45));
	persons.add(new Person("TestName", 0));
	persons.add(new Person("TestName", 3));
	persons.add(new Person("TestName", 45));
	persons.add(new Person("TestName", 130));
	persons.add(new Person("TestName", 33));
	persons.add(new Person("TestName", 56));
    }

    @After
    public void cleanup() {
	persons.clear();
    }

}
