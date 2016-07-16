package com.swayam.demo.reactive.reactor1.react;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class JaxbUnmarshaller {

    @SuppressWarnings("unchecked")
    public <T> T unmarshall(InputStream inputStream, Class<T> rootElementClass) {
	JAXBContext jaxbContext;
	try {
	    jaxbContext = JAXBContext.newInstance(rootElementClass);
	} catch (JAXBException e) {
	    throw new RuntimeException(e);
	}

	Unmarshaller unmarshaller;

	try {
	    unmarshaller = jaxbContext.createUnmarshaller();
	} catch (JAXBException e) {
	    throw new RuntimeException(e);
	}

	try {
	    return (T) unmarshaller.unmarshal(inputStream);
	} catch (JAXBException e) {
	    throw new RuntimeException(e);
	}

    }

}
