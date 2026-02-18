package com.swayam.blog.demo.avro;

import java.util.Date;

import org.apache.avro.Conversion;
import org.apache.avro.LogicalType;
import org.apache.avro.Schema;

public class DateConversion extends Conversion<Date> {

	@Override
	public Class<Date> getConvertedType() {
		return Date.class;
	}

	@Override
	public String getLogicalTypeName() {
		return DateLogicalType.NAME;
	}

	@Override
	public Date fromLong(Long value, Schema schema, LogicalType type) {
		Date date = new Date();
		date.setTime(value);
		return date;
	}

	@Override
	public Long toLong(Date value, Schema schema, LogicalType type) {
		return value.getTime();
	}

	@Override
	public Schema getRecommendedSchema() {
		return new DateLogicalType().addToSchema(Schema.create(Schema.Type.LONG));
	}

}
