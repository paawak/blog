package com.swayam.blog.demo.avro;

import java.lang.annotation.Annotation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

public class CustomJacksonAnnotationIntrospector extends JacksonAnnotationIntrospector {

	private static final long serialVersionUID = 1L;

	@Override
	protected <A extends Annotation> A _findAnnotation(Annotated ann, Class<A> annoClass) {
		if (JsonProperty.class.equals(annoClass)) {
			return null;
		}
		return super._findAnnotation(ann, annoClass);
	}

}
