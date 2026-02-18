package com.swayam.blog.demo.avro;

import org.apache.avro.LogicalType;
import org.apache.avro.LogicalTypes.LogicalTypeFactory;
import org.apache.avro.Schema;

public class DateLogicalTypeFactory implements LogicalTypeFactory {

	@Override
	public LogicalType fromSchema(Schema schema) {
		return new DateLogicalType();
	}

	@Override
	public String getTypeName() {
		return DateLogicalType.NAME;
	}

}
