package com.swayam.demo.reactive.rxjava;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlParser<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlParser.class);

    private final String xmlElementName;
    private final Class<T> classToUnmarshall;
    private final CountDownLatch countDownLatch;

    private final JaxbUnmarshaller jaxbUnmarshaller;
    private final BlockingQueue<T> blockingQueue;
    private final StaxSpliterator<T> staxSpliterator;
    private final Stream<T> stream;

    public XmlParser(String xmlElementName, Class<T> classToUnmarshall, CountDownLatch countDownLatch) {
	this.xmlElementName = xmlElementName;
	this.classToUnmarshall = classToUnmarshall;
	this.countDownLatch = countDownLatch;

	jaxbUnmarshaller = new JaxbUnmarshaller();

	blockingQueue = new ArrayBlockingQueue<>(10_000);

	staxSpliterator = new StaxSpliterator<>(blockingQueue);

	stream = StreamSupport.stream(staxSpliterator, true);
    }

    public Stream<T> parse(InputStream inputStream) {

	Runnable doParse = () -> {
	    try {
		doParse(inputStream);
	    } catch (XMLStreamException e) {
		throw new RuntimeException(e);
	    }
	};

	new Thread(doParse).start();

	return stream;
    }

    private void doParse(InputStream inputStream) throws XMLStreamException {
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

		    LOGGER.trace("publishing: {}", newElement);

		    blockingQueue.add(newElement);

		    buffer.setLength(0);
		}
	    } else if (eventType == XMLStreamConstants.CHARACTERS) {
		buffer.append(xmlStreamReader.getText().trim());
	    } else if (eventType == XMLStreamConstants.END_DOCUMENT) {
		staxSpliterator.endOfDocument();
		LOGGER.info("end of xml document");
		countDownLatch.countDown();
	    }
	}
    }

    private StringBuilder newStringBuilder() {
	return new StringBuilder(600);
    }

}
