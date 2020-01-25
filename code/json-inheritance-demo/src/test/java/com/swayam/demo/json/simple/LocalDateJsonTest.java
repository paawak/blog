package com.swayam.demo.json.simple;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.time.LocalDate;

import org.hamcrest.core.StringStartsWith;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class LocalDateJsonTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testJavaToJson_no_JavaTimeModule() throws JsonProcessingException {
		String expectedJson = "{\"id\":100,\"firstName\":\"Subodh\",\"lastName\":\"Ghosh\",\"dateOfBirth\":";
		Person person = new Person(100, "Subodh", "Ghosh", LocalDate.of(1909, 9, 14));
		ObjectMapper mapper = new ObjectMapper();
		String personJson = mapper.writeValueAsString(person);
		assertTrue(new StringStartsWith(expectedJson).matches(personJson));
		assertTrue(personJson.contains("chronology"));
		assertTrue(personJson.contains("calendarType"));
		assertTrue(personJson.contains("dayOfWeek"));
	}

	@Test
	public void testJsonToJava_no_JavaTimeModule() throws JsonParseException, JsonMappingException, IOException {
		String personJson = "{\"id\":100,\"firstName\":\"Subodh\",\"lastName\":\"Ghosh\",\"dateOfBirth\":{\"year\":1909,\"month\":\"SEPTEMBER\",\"dayOfMonth\":14,\"dayOfWeek\":\"TUESDAY\",\"era\":\"CE\",\"dayOfYear\":257,\"leapYear\":false,\"monthValue\":9,\"chronology\":{\"id\":\"ISO\",\"calendarType\":\"iso8601\"}}}";

		ObjectMapper mapper = new ObjectMapper();

		thrown.expect(JsonMappingException.class);

		thrown.expectMessage(new StringStartsWith(
				"Cannot construct instance of `java.time.LocalDate` (no Creators, like default construct, exist)"));

		try {
			mapper.readValue(personJson, Person.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Test
	public void testJavaToJson_with_JavaTimeModule() throws JsonProcessingException {
		String expectedJson = "{\"id\":100,\"firstName\":\"Subodh\",\"lastName\":\"Ghosh\",\"dateOfBirth\":[1909,9,14]}";
		Person person = new Person(100, "Subodh", "Ghosh", LocalDate.of(1909, 9, 14));
		ObjectMapper mapper = new ObjectMapper();
		// register all data type modules
		// this may have performance implications:
		// mapper.findAndRegisterModules();
		mapper.registerModule(new JavaTimeModule());
		String personJson = mapper.writeValueAsString(person);
		assertEquals(expectedJson, personJson);
	}

	@Test
	public void testJsonToJava_with_JavaTimeModule() throws JsonParseException, JsonMappingException, IOException {
		Person expectedPerson = new Person(100, "Subodh", "Ghosh", LocalDate.of(1909, 9, 14));
		String personJson = "{\"id\":100,\"firstName\":\"Subodh\",\"lastName\":\"Ghosh\",\"dateOfBirth\":[1909,9,14]}";

		ObjectMapper mapper = new ObjectMapper();
		// register all data type modules
		// this may have performance implications:
		// mapper.findAndRegisterModules();
		mapper.registerModule(new JavaTimeModule());
		Person person = mapper.readValue(personJson, Person.class);
		assertEquals(expectedPerson, person);
	}

}
