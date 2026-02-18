package com.swayam.blog.demo.avro;

import java.util.Date;

import org.apache.avro.Schema;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.fasterxml.jackson.dataformat.avro.AvroAnnotationIntrospector;
import com.fasterxml.jackson.dataformat.avro.AvroMapper;
import com.fasterxml.jackson.dataformat.avro.jsr310.AvroJavaTimeModule;
import com.fasterxml.jackson.dataformat.avro.schema.AvroSchemaGenerator;
import com.fasterxml.jackson.dataformat.avro.schema.DefinedSchemas;
import com.fasterxml.jackson.dataformat.avro.schema.VisitorFormatWrapperImpl;

public class AvroSchemaGenerationDemo {

	public Schema generateSimpleSchema(Class<?> pojoClass) throws JsonMappingException {
		AvroMapper avroMapper = AvroMapper.builder().addModule(new AvroJavaTimeModule()).build();
		AvroSchemaGenerator avroSchemaGenerator = new AvroSchemaGenerator();
		// Enable logical types
		avroSchemaGenerator.enableLogicalTypes();

		avroMapper.acceptJsonFormatVisitor(pojoClass, avroSchemaGenerator);
		return avroSchemaGenerator.getGeneratedSchema().getAvroSchema();
	}

	public Schema generateCustomizedSchema(Class<?> pojoClass) throws JsonMappingException {
		DefinedSchemas definedSchemas = new DefinedSchemas();
		definedSchemas.addSchema(SimpleType.constructUnsafe(Date.class), new DateConversion().getRecommendedSchema());

		VisitorFormatWrapperImpl jsonFormatVisitorWrapper = new VisitorFormatWrapperImpl(definedSchemas, null)
				.enableLogicalTypes();

		AvroMapper avroMapper = AvroMapper.builder().annotationIntrospector(AnnotationIntrospector
				.pair(new AvroAnnotationIntrospector(), new CustomJacksonAnnotationIntrospector()))
				.addModule(new AvroJavaTimeModule()).build();
		// Enable logical types
		jsonFormatVisitorWrapper.enableLogicalTypes();

		avroMapper.acceptJsonFormatVisitor(pojoClass, jsonFormatVisitorWrapper);
		return jsonFormatVisitorWrapper.getAvroSchema();
	}

}
