package com.swayam.demo.reactive.reactor2.react;

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

import com.swayam.demo.reactive.reactor2.model.LineItemRow;

import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

public class XmlParserReact2 {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlParserReact2.class);

    private static final String XML_ELEMENT_NAME = "T";

    private final CountDownLatch countDownLatch;

    private final JaxbUnmarshaller jaxbUnmarshaller;

    private final UnicastProcessor<LineItemRow> processor;

    public XmlParserReact2(CountDownLatch countDownLatch) {
	this.countDownLatch = countDownLatch;
	jaxbUnmarshaller = new JaxbUnmarshaller();

	processor = UnicastProcessor.create();

    }

    public Flux<LineItemRow> parse(InputStream inputStream) {

	Runnable doParse = () -> {
	    try {
		doParse(inputStream);
	    } catch (XMLStreamException xmlStreamException) {
		processor.onError(xmlStreamException);
	    }
	};

	new Thread(doParse).start();

	return processor;
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

		    LineItemRow newElement = jaxbUnmarshaller.unmarshall(new ByteArrayInputStream(buffer.toString().getBytes(StandardCharsets.UTF_8)), LineItemRow.class);

		    buffer = newStringBuilder();

		    processor.onNext(newElement);
		}
	    } else if (eventType == XMLStreamConstants.CHARACTERS) {
		buffer.append(xmlStreamReader.getText().trim());
	    } else if (eventType == XMLStreamConstants.END_DOCUMENT) {
		// TODO:: not sure how to signal the end of document
		countDownLatch.countDown();
		processor.onComplete();
		LOGGER.info("end of xml document");
	    }
	}

    }

    private StringBuilder newStringBuilder() {
	return new StringBuilder(600);
    }

}
