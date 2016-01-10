package com.swayam.demo.json.simple;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class SimpleJsonTest {

    @Test
    public void testJavaToJson() throws JsonProcessingException {
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
    public void testJsonToJava() throws JsonParseException, JsonMappingException, IOException {
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
