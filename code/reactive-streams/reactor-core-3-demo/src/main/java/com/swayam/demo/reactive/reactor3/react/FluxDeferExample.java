package com.swayam.demo.reactive.reactor3.react;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import java.util.function.Supplier;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.swayam.demo.reactive.reactor3.model.LineItemRow;

import reactor.core.publisher.Flux;

public class FluxDeferExample {

	private static final Logger LOGGER = LoggerFactory.getLogger(FluxDeferExample.class);

	private static final String XML_ELEMENT_NAME = "T";

	private final CountDownLatch countDownLatch;

	private final JaxbUnmarshaller jaxbUnmarshaller;

	public FluxDeferExample(CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
		jaxbUnmarshaller = new JaxbUnmarshaller();
	}

	public Flux<LineItemRow> parse(InputStream inputStream) {

		Supplier<Publisher<LineItemRow>> supplier = () -> {
			Publisher<LineItemRow> lineItemRowPublisher = (Subscriber<? super LineItemRow> lineItemRowSubscriber) -> {
				Runnable doParse = () -> {
					try {
						doParse(inputStream, lineItemRowSubscriber);
					} catch (XMLStreamException xmlStreamException) {
						lineItemRowSubscriber.onError(xmlStreamException);
					}
				};
				new Thread(doParse).start();
			};
			return lineItemRowPublisher;
		};
		Flux<LineItemRow> flux = Flux.defer(supplier);

		return flux;
	}

	private void doParse(InputStream inputStream, Subscriber<? super LineItemRow> lineItemRowSubscriber)
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

					LineItemRow newElement = jaxbUnmarshaller.unmarshall(
							new ByteArrayInputStream(buffer.toString().getBytes(StandardCharsets.UTF_8)),
							LineItemRow.class);

					LOGGER.debug("publishing: {}", newElement);
					lineItemRowSubscriber.onNext(newElement);

					buffer.setLength(0);
				}
			} else if (eventType == XMLStreamConstants.CHARACTERS) {
				buffer.append(xmlStreamReader.getText().trim());
			} else if (eventType == XMLStreamConstants.END_DOCUMENT) {
				LOGGER.info("end of xml document");
				lineItemRowSubscriber.onComplete();
				countDownLatch.countDown();
			}
		}

	}

	private StringBuilder newStringBuilder() {
		return new StringBuilder(600);
	}

}
