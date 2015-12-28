package com.swayam.demo.json.simple;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swayam.demo.json.simple.Person;

public class SimpleJsonTest {

    @Test
    public void testJavaToJson() throws JsonProcessingException {
	String expectedJson = "{\"id\":100,\"firstName\":\"Subodh\",\"lastName\":\"Ghosh\"}";
	Person person = new Person(100, "Subodh", "Ghosh");
	ObjectMapper mapper = new ObjectMapper();
	String personJson = mapper.writeValueAsString(person);
	assertEquals(expectedJson, personJson);
    }

    @Test
    public void testJsonToJava() throws JsonParseException, JsonMappingException, IOException {
	Person expectedPerson = new Person(100, "Subodh", "Ghosh");
	String personJson = "{\"id\":100,\"firstName\":\"Subodh\",\"lastName\":\"Ghosh\"}";

	ObjectMapper mapper = new ObjectMapper();
	Person person = mapper.readValue(personJson, Person.class);
	assertEquals(expectedPerson, person);
    }

}
