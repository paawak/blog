package com.swayam.demo.json.polymorphic.withannotation;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PolymorphicJsonTest {

    @Test
    public void testJavaToJson_lizard() throws JsonProcessingException {
	String expectedJson = "{\"type\":\"com.swayam.demo.json.polymorphic.withannotation.Lizard\",\"species\":\"Varanus bengalensis\",\"commonName\":\"Bengal monitor\",\"legs\":4}";
	Animal lizard = new Lizard("Varanus bengalensis", "Bengal monitor", 4);
	ObjectMapper mapper = new ObjectMapper();
	String lizardJson = mapper.writeValueAsString(lizard);
	assertEquals(expectedJson, lizardJson);
    }

    @Test
    public void testJavaToJson_bird() throws JsonProcessingException {
	String expectedJson = "{\"type\":\"com.swayam.demo.json.polymorphic.withannotation.Bird\",\"species\":\"Copsychus saularis\",\"commonName\":\"Doel, Oriental magpie-robin\",\"legs\":2}";
	Animal bird = new Bird("Copsychus saularis", "Doel, Oriental magpie-robin", 2);
	ObjectMapper mapper = new ObjectMapper();
	String birdJson = mapper.writeValueAsString(bird);
	assertEquals(expectedJson, birdJson);
    }

    @Test
    public void testJsonToJava_lizard() throws JsonParseException, JsonMappingException, IOException {
	Animal expectedLizard = new Lizard("Varanus bengalensis", "Bengal monitor", 4);
	String lizardJson = "{\"type\":\"com.swayam.demo.json.polymorphic.withannotation.Lizard\",\"species\":\"Varanus bengalensis\",\"commonName\":\"Bengal monitor\",\"legs\":4}";

	ObjectMapper mapper = new ObjectMapper();
	Animal lizard = mapper.readValue(lizardJson, Animal.class);

	assertEquals(expectedLizard, lizard);
    }

    @Test
    public void testJsonToJava_bird() throws JsonParseException, JsonMappingException, IOException {
	Animal expectedBird = new Bird("Copsychus saularis", "Doel, Oriental magpie-robin", 2);
	String birdJson = "{\"type\":\"com.swayam.demo.json.polymorphic.withannotation.Bird\",\"species\":\"Copsychus saularis\",\"commonName\":\"Doel, Oriental magpie-robin\",\"legs\":2}";

	ObjectMapper mapper = new ObjectMapper();
	Animal bird = mapper.readValue(birdJson, Animal.class);

	assertEquals(expectedBird, bird);
    }

}
