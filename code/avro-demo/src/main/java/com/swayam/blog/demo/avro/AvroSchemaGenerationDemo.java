package com.swayam.blog.demo.avro;

import org.apache.avro.Schema;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.avro.AvroMapper;
import com.fasterxml.jackson.dataformat.avro.jsr310.AvroJavaTimeModule;
import com.fasterxml.jackson.dataformat.avro.schema.AvroSchemaGenerator;

public class AvroSchemaGenerationDemo {

	public Schema generateSimpleSchema(Class<?> pojoClass) throws JsonMappingException {
		AvroMapper mapper = AvroMapper.builder().addModule(new AvroJavaTimeModule()).build();
		AvroSchemaGenerator gen = new AvroSchemaGenerator();
		// Enable logical types
		gen.enableLogicalTypes();

		mapper.acceptJsonFormatVisitor(pojoClass, gen);
		return gen.getGeneratedSchema().getAvroSchema();
	}

}
