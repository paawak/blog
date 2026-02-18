package com.swayam.blog.demo.avro;

import org.apache.avro.LogicalType;

public class DateLogicalType extends LogicalType {

	public static final String NAME = "date-millis";

	public DateLogicalType() {
		super(NAME);
	}

}
