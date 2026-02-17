package com.swayam.blog.demo.avro;

import java.util.Date;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Type;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.fasterxml.jackson.dataformat.avro.AvroAnnotationIntrospector;
import com.fasterxml.jackson.dataformat.avro.AvroMapper;
import com.fasterxml.jackson.dataformat.avro.jsr310.AvroJavaTimeModule;
import com.fasterxml.jackson.dataformat.avro.schema.AvroSchemaGenerator;
import com.fasterxml.jackson.dataformat.avro.schema.DefinedSchemas;
import com.fasterxml.jackson.dataformat.avro.schema.VisitorFormatWrapperImpl;

public class AvroSchemaGenerationDemo {

	public Schema generateSimpleSchema(Class<?> pojoClass) throws JsonMappingException {
		AvroMapper mapper = AvroMapper.builder().addModule(new AvroJavaTimeModule()).build();
		AvroSchemaGenerator avroSchemaGenerator = new AvroSchemaGenerator();
		// Enable logical types
		avroSchemaGenerator.enableLogicalTypes();

		mapper.acceptJsonFormatVisitor(pojoClass, avroSchemaGenerator);
		return avroSchemaGenerator.getGeneratedSchema().getAvroSchema();
	}

	public Schema generateCustomizedSchema(Class<?> pojoClass) throws JsonMappingException {

		DefinedSchemas ds = new DefinedSchemas();
		ds.addSchema(SimpleType.constructUnsafe(Date.class), Schema.create(Type.LONG));
		VisitorFormatWrapperImpl jsonFormatVisitorWrapper = new VisitorFormatWrapperImpl(ds, null).enableLogicalTypes();

		AnnotationIntrospector ai = AnnotationIntrospector.pair(new AvroAnnotationIntrospector(),
				new JacksonAnnotationIntrospector());

		AvroMapper mapper = AvroMapper.builder().annotationIntrospector(ai).addModule(new AvroJavaTimeModule()).build();
		// Enable logical types
		jsonFormatVisitorWrapper.enableLogicalTypes();

		mapper.acceptJsonFormatVisitor(pojoClass, jsonFormatVisitorWrapper);
		return jsonFormatVisitorWrapper.getAvroSchema();
	}

}
