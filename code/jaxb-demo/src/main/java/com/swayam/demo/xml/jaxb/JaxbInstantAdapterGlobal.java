package com.swayam.demo.xml.jaxb;

import java.time.Instant;

public class JaxbInstantAdapterGlobal extends JaxbInstantAdapterField {

    @Override
    public Instant unmarshal(String instant) throws Exception {
	return Instant.parse(instant);
    }

}
