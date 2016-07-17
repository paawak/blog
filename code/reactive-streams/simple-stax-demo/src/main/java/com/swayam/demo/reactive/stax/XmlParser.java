package com.swayam.demo.reactive.stax;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlParser<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlParser.class);

    private final StaxListener<T> staxListener;
    private final String xmlElementName;
    private final JaxbUnmarshaller jaxbUnmarshaller;
    private final Class<T> classToUnmarshall;

    public XmlParser(StaxListener<T> staxListener, String xmlElementName, Class<T> classToUnmarshall) {
	this.staxListener = staxListener;
	this.xmlElementName = xmlElementName;
	this.classToUnmarshall = classToUnmarshall;
	jaxbUnmarshaller = new JaxbUnmarshaller();
    }

    public void parse(InputStream inputStream) throws XMLStreamException {
	XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
	XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(inputStream);

	StringBuilder buffer = newStringBuilder();

	while (xmlStreamReader.hasNext()) {
	    int eventType = xmlStreamReader.next();
	    if (eventType == XMLStreamConstants.START_ELEMENT) {
		String element = xmlStreamReader.getLocalName();

		if (xmlElementName.equals(element)) {
		    buffer = newStringBuilder();
		}

		buffer.append("<").append(element).append(">");

	    } else if (eventType == XMLStreamConstants.END_ELEMENT) {

		String element = xmlStreamReader.getLocalName();

		buffer.append("</").append(element).append(">");

		if (xmlElementName.equals(element)) {

		    T newElement = jaxbUnmarshaller.unmarshall(new ByteArrayInputStream(buffer.toString().getBytes(StandardCharsets.UTF_8)), classToUnmarshall);

		    staxListener.newElement(newElement);

		    buffer.setLength(0);
		}
	    } else if (eventType == XMLStreamConstants.CHARACTERS) {
		buffer.append(xmlStreamReader.getText().trim());
	    } else if (eventType == XMLStreamConstants.END_DOCUMENT) {
		staxListener.endOfDocument();
		LOGGER.info("end of xml document");
	    }
	}
    }

    private StringBuilder newStringBuilder() {
	return new StringBuilder(600);
    }

}
