package com.swayam.demo.sort;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PersonComparatorBadTest {

    // @Rule
    // public ExpectedException thrown = ExpectedException.none();

    private final List<Person> persons = new ArrayList<>();

    @Test
    public void testCompare() {
	// thrown.expect(IllegalArgumentException.class);
	// thrown.expectMessage("Comparison method violates its general
	// contract!");

	// I want the exception trace to be printed out
	try {
	    Collections.sort(persons, new PersonComparatorBad());
	    fail("Expecting a " + IllegalArgumentException.class);
	} catch (IllegalArgumentException e) {
	    e.printStackTrace();
	    assertTrue(true);
	}

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
