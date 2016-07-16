package com.swayam.demo.reactive.reactor1.react;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.swayam.demo.reactive.reactor1.model.LineItemRow;

import reactor.core.Environment;
import reactor.core.composable.Deferred;
import reactor.core.composable.Stream;
import reactor.core.composable.spec.Streams;
import reactor.event.dispatch.ThreadPoolExecutorDispatcher;

public class XmlParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlParser.class);

    private static final String XML_ELEMENT_NAME = "T";

    private final Environment environment;
    private final CountDownLatch countDownLatch;

    private final JaxbUnmarshaller jaxbUnmarshaller;

    private final Deferred<LineItemRow, Stream<LineItemRow>> deferred;

    public XmlParser(Environment environment, CountDownLatch countDownLatch) {
	this.environment = environment;
	this.countDownLatch = countDownLatch;
	jaxbUnmarshaller = new JaxbUnmarshaller();
	deferred = Streams.defer(environment);
    }

    public Stream<LineItemRow> parse(InputStream inputStream) {

	Runnable doParse = () -> {
	    try {
		doParse(inputStream);
	    } catch (XMLStreamException xmlStreamException) {
		deferred.accept(xmlStreamException);
	    }
	};

	environment.getDispatcher(ThreadPoolExecutorDispatcher.class.getSimpleName()).execute(doParse);

	return deferred.compose();
    }

    private void doParse(InputStream inputStream) throws XMLStreamException {
	XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
	XMLStreamReader xmlStreamReader;
	xmlStreamReader = xmlInputFactory.createXMLStreamReader(inputStream);

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

		    deferred.accept(newElement);
		}
	    } else if (eventType == XMLStreamConstants.CHARACTERS) {
		buffer.append(xmlStreamReader.getText().trim());
	    } else if (eventType == XMLStreamConstants.END_DOCUMENT) {
		// TODO:: not sure how to signal the end of document
		countDownLatch.countDown();
		LOGGER.info("end of xml document");
	    }
	}

    }

    private StringBuilder newStringBuilder() {
	return new StringBuilder(600);
    }

}
