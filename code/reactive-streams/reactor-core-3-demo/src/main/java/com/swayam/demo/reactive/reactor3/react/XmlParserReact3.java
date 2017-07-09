package com.swayam.demo.reactive.reactor3.react;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Stream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.swayam.demo.reactive.reactor3.model.LineItemRow;

import reactor.core.publisher.UnicastProcessor;

public class XmlParserReact3 {

	private static final Logger LOGGER = LoggerFactory.getLogger(XmlParserReact3.class);

	private static final String XML_ELEMENT_NAME = "T";

	private final CountDownLatch countDownLatch;

	private final JaxbUnmarshaller jaxbUnmarshaller;

	private final UnicastProcessor<LineItemRow> processor;

	public XmlParserReact3(CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
		jaxbUnmarshaller = new JaxbUnmarshaller();

		processor = UnicastProcessor.create();

	}

	public Stream<LineItemRow> parse(InputStream inputStream) {

		Runnable doParse = () -> {
			try {
				doParse(inputStream);
			} catch (XMLStreamException xmlStreamException) {
				processor.onError(xmlStreamException);
			}
		};

		new Thread(doParse).start();

		return processor.toStream();
	}

	private void doParse(InputStream inputStream) throws XMLStreamException {
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

					LineItemRow newElement = jaxbUnmarshaller.unmarshall(
							new ByteArrayInputStream(buffer.toString().getBytes(StandardCharsets.UTF_8)),
							LineItemRow.class);

					LOGGER.trace("publishing: {}", newElement);
					processor.onNext(newElement);

					buffer.setLength(0);
				}
			} else if (eventType == XMLStreamConstants.CHARACTERS) {
				buffer.append(xmlStreamReader.getText().trim());
			} else if (eventType == XMLStreamConstants.END_DOCUMENT) {
				LOGGER.info("end of xml document");
				processor.onComplete();
				countDownLatch.countDown();
			}
		}

	}

	private StringBuilder newStringBuilder() {
		return new StringBuilder(600);
	}

}
