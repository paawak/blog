package com.swayam.demo.reactive.reactor3.react.impl;

import java.io.InputStream;
import java.util.function.Supplier;

import javax.xml.stream.XMLStreamException;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.swayam.demo.reactive.reactor3.model.LineItemRow;
import com.swayam.demo.reactive.reactor3.react.ReactiveXmlParser;
import com.swayam.demo.reactive.reactor3.xml.XmlEventListener;
import com.swayam.demo.reactive.reactor3.xml.XmlParser;

import reactor.core.publisher.Flux;

public class FluxDeferExample implements ReactiveXmlParser {

	private static final Logger LOGGER = LoggerFactory.getLogger(FluxDeferExample.class);

	@Override
	public Flux<LineItemRow> parse(InputStream inputStream) {

		Publisher<LineItemRow> lineItemRowPublisher = (Subscriber<? super LineItemRow> lineItemRowSubscriber) -> {
			try {
				XmlParser xmlParser = new XmlParser();
				xmlParser.parse(inputStream, LineItemRow.class, new XmlEventListener<LineItemRow>() {

					@Override
					public void element(LineItemRow element) {
						lineItemRowSubscriber.onNext(element);
					}

					@Override
					public void completed() {
						lineItemRowSubscriber.onComplete();
						LOGGER.info("sent completed signal");
					}
				});
			} catch (XMLStreamException xmlStreamException) {
				lineItemRowSubscriber.onError(xmlStreamException);
			}

		};

		Supplier<Publisher<LineItemRow>> supplier = () -> {
			return lineItemRowPublisher;
		};

		return Flux.defer(supplier);
	}

}
