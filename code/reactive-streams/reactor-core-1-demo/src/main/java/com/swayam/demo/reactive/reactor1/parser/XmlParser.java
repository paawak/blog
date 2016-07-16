package com.swayam.demo.reactive.reactor1.parser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.swayam.demo.reactive.reactor1.StaxListener;
import com.swayam.demo.reactive.reactor1.model.LineItemRow;

public class XmlParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlParser.class);

    private static final String XML_ELEMENT_NAME = "T";

    private final StaxListener<LineItemRow> staxListener;
    private final JaxbUnmarshaller jaxbUnmarshaller;

    public XmlParser(StaxListener<LineItemRow> staxListener) {
	this.staxListener = staxListener;
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

		// FIXME: bad hack
		if ("table".equals(element)) {
		    continue;
		}

		buffer.append("<").append(element).append(">");

	    } else if (eventType == XMLStreamConstants.END_ELEMENT) {

		String element = xmlStreamReader.getLocalName();

		// FIXME: bad hack
		if ("table".equals(element)) {
		    continue;
		}

		buffer.append("</").append(element).append(">");

		if (XML_ELEMENT_NAME.equals(element)) {

		    LineItemRow newElement = jaxbUnmarshaller.unmarshall(new ByteArrayInputStream(buffer.toString().getBytes(StandardCharsets.UTF_8)), LineItemRowImpl.class);

		    buffer = newStringBuilder();

		    staxListener.newElement(newElement);
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
