package com.swayam.demo.reactive.reactor3.react;

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

import com.swayam.demo.reactive.reactor3.model.LineItemRow;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

public class FluxCreateExample {

	private static final Logger LOGGER = LoggerFactory.getLogger(FluxCreateExample.class);

	private static final String XML_ELEMENT_NAME = "T";

	private final CountDownLatch countDownLatch;

	private final JaxbUnmarshaller jaxbUnmarshaller;

	public FluxCreateExample(CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
		jaxbUnmarshaller = new JaxbUnmarshaller();
	}

	public Flux<LineItemRow> parse(InputStream inputStream) {

		Flux<LineItemRow> flux = Flux.create((FluxSink<LineItemRow> fluxSink) -> {
			Runnable doParse = () -> {
				try {
					doParse(inputStream, fluxSink);
				} catch (XMLStreamException xmlStreamException) {
					fluxSink.error(xmlStreamException);
				}
			};
			new Thread(doParse).start();
		});

		return flux;
	}

	private void doParse(InputStream inputStream, FluxSink<LineItemRow> fluxSink) throws XMLStreamException {
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

					LOGGER.debug("publishing: {}", newElement);
					fluxSink.next(newElement);

					buffer.setLength(0);
				}
			} else if (eventType == XMLStreamConstants.CHARACTERS) {
				buffer.append(xmlStreamReader.getText().trim());
			} else if (eventType == XMLStreamConstants.END_DOCUMENT) {
				LOGGER.info("end of xml document");
				fluxSink.complete();
				countDownLatch.countDown();
			}
		}

	}

	private StringBuilder newStringBuilder() {
		return new StringBuilder(600);
	}

}
