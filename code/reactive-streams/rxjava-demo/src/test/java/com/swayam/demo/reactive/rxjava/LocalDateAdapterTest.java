package com.swayam.demo.reactive.rxjava;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

import com.swayam.demo.reactive.rxjava.LocalDateAdapter;

public class LocalDateAdapterTest {

    @Test
    public void testUnmarshal() {
	// given
	LocalDateAdapter testClass = new LocalDateAdapter();

	// when
	LocalDate result = testClass.unmarshal("1996-03-13");

	// then
	assertEquals(LocalDate.of(1996, 3, 13), result);

    }

    @Test
    public void testMarshal() {
	// given
	LocalDateAdapter testClass = new LocalDateAdapter();

	// when
	String result = testClass.marshal(LocalDate.of(1996, 3, 13));

	// then
	assertEquals("1996-03-13", result);

    }

}
