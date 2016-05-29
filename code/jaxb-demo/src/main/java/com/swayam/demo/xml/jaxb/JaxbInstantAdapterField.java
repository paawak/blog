package com.swayam.demo.xml.jaxb;

import java.time.Instant;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class JaxbInstantAdapterField extends XmlAdapter<String, Instant> {

    @Override
    public String marshal(Instant instant) throws Exception {
	return instant.toString();
    }

    @Override
    public Instant unmarshal(String instant) throws Exception {
	if (true) {
	    throw new IllegalArgumentException();
	}
	return Instant.parse(instant);
    }

}
