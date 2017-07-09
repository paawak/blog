package com.swayam.demo.reactive.reactor3.xml;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlParser {

	private static final Logger LOGGER = LoggerFactory.getLogger(XmlParser.class);

	private static final String XML_ELEMENT_NAME = "T";

	private final JaxbUnmarshaller jaxbUnmarshaller;

	public XmlParser() {
		jaxbUnmarshaller = new JaxbUnmarshaller();
	}

	public <T> void parse(InputStream inputStream, Class<T> rootElementClass, XmlEventListener<T> xmlEventListener)
			throws XMLStreamException {
		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		XMLStreamReader xmlStreamReader;
		xmlStreamReader = xmlInputFactory.createXMLStreamReader(inputStream);

		StringBuilder buffer = newStringBuilder();

		while (xmlStreamReader.hasNext()) {

			int eventType = xmlStreamReader.next();
			if (eventType == XMLStreamConstants.START_DOCUMENT) {
				LOGGER.info("started parsing document");
			} else if (eventType == XMLStreamConstants.START_ELEMENT) {
				String element = xmlStreamReader.getLocalName();

				if (XML_ELEMENT_NAME.equals(element)) {
					buffer = newStringBuilder();
				}

				buffer.append("<").append(element).append(">");

			} else if (eventType == XMLStreamConstants.END_ELEMENT) {

				String element = xmlStreamReader.getLocalName();

				buffer.append("</").append(element).append(">");

				if (XML_ELEMENT_NAME.equals(element)) {

					T newElement = jaxbUnmarshaller.unmarshall(
							new ByteArrayInputStream(buffer.toString().getBytes(StandardCharsets.UTF_8)),
							rootElementClass);

					LOGGER.debug("new element: {}", newElement);
					xmlEventListener.element(newElement);

					buffer.setLength(0);
				}
			} else if (eventType == XMLStreamConstants.CHARACTERS) {
				buffer.append(xmlStreamReader.getText().trim());
			} else if (eventType == XMLStreamConstants.END_DOCUMENT) {
				LOGGER.info("end of xml document");
				xmlEventListener.completed();
			}
		}

	}

	private StringBuilder newStringBuilder() {
		return new StringBuilder(600);
	}

}
