package com.swayam.demo.reactive.reactor3.xml;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    private final DateTimeFormatter dateTimeFormatter;

    public LocalDateAdapter() {
	dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    @Override
    public LocalDate unmarshal(String dateString) {
	return LocalDate.parse(dateString, dateTimeFormatter);
    }

    @Override
    public String marshal(LocalDate date) {
	return date.format(dateTimeFormatter);
    }

}
