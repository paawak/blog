package com.swayam.demo.reactive.stax;

import java.io.InputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlParser.class);

    public void parse(InputStream inputStream) throws XMLStreamException {
	XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
	XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(inputStream);
	while (xmlStreamReader.hasNext()) {
	    int eventType = xmlStreamReader.next();
	    if (eventType == XMLStreamConstants.CHARACTERS) {
		LOGGER.info("CHARACTERS: `{}`", xmlStreamReader.getText().trim());
	    } else if (eventType == XMLStreamConstants.START_ELEMENT) {
		LOGGER.info("START_ELEMENT: {}", xmlStreamReader.getLocalName());
	    }
	}
    }

}
