package com.swayam.demo.sort;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class PersonComparatorTransitiveTest {

    @Test
    public void testCompare() {
        List<Person> persons = PersonComparatorNonTransitiveTest.getDataSetForTimSortException();
        Collections.sort(persons, new PersonComparatorTransitive());
        persons.forEach((Person person) -> {
            System.out.println(person.getId());
        });
    }

}
