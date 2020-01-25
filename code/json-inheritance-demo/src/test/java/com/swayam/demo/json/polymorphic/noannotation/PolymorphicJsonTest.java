package com.swayam.demo.json.polymorphic.noannotation;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.hamcrest.core.StringStartsWith;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PolymorphicJsonTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testJavaToJson_lizard() throws JsonProcessingException {
		String expectedJson = "{\"species\":\"Varanus bengalensis\",\"commonName\":\"Bengal monitor\",\"legs\":4}";
		Animal lizard = new Lizard("Varanus bengalensis", "Bengal monitor", 4);
		ObjectMapper mapper = new ObjectMapper();
		String lizardJson = mapper.writeValueAsString(lizard);
		assertEquals(expectedJson, lizardJson);
	}

	@Test
	public void testJavaToJson_bird() throws JsonProcessingException {
		String expectedJson = "{\"species\":\"Copsychus saularis\",\"commonName\":\"Doel, Oriental magpie-robin\",\"legs\":2}";
		Animal bird = new Bird("Copsychus saularis", "Doel, Oriental magpie-robin", 2);
		ObjectMapper mapper = new ObjectMapper();
		String birdJson = mapper.writeValueAsString(bird);
		assertEquals(expectedJson, birdJson);
	}

	@Test
	public void testJsonToJava_lizard() throws JsonParseException, JsonMappingException, IOException {
		thrown.expect(JsonMappingException.class);
		thrown.expectMessage(new StringStartsWith(
				"Cannot construct instance of `com.swayam.demo.json.polymorphic.noannotation.Animal` (no Creators, like default construct, exist):"));

		String lizardJson = "{\"species\":\"Varanus bengalensis\",\"commonName\":\"Bengal monitor\",\"legs\":4}";

		ObjectMapper mapper = new ObjectMapper();

		try {
			mapper.readValue(lizardJson, Animal.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
			throw e;
		}

	}

	@Test
	public void testJsonToJava_bird() throws JsonParseException, JsonMappingException, IOException {
		thrown.expect(JsonMappingException.class);
		thrown.expectMessage(new StringStartsWith(
				"Cannot construct instance of `com.swayam.demo.json.polymorphic.noannotation.Animal` (no Creators, like default construct, exist):"));

		String birdJson = "{\"species\":\"Copsychus saularis\",\"commonName\":\"Doel, Oriental magpie-robin\",\"legs\":2}";

		ObjectMapper mapper = new ObjectMapper();

		mapper.readValue(birdJson, Animal.class);

	}

}
