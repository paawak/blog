package com.swayam.demo.reactive.reactor3.react.impl;

import java.io.InputStream;

import javax.xml.stream.XMLStreamException;

import com.swayam.demo.reactive.reactor3.model.LineItemRow;
import com.swayam.demo.reactive.reactor3.react.ReactiveXmlParser;
import com.swayam.demo.reactive.reactor3.xml.XmlEventListener;
import com.swayam.demo.reactive.reactor3.xml.XmlParser;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

public class FluxCreateExample implements ReactiveXmlParser {

	@Override
	public Flux<LineItemRow> parse(InputStream inputStream) {

		Flux<LineItemRow> flux = Flux.create((FluxSink<LineItemRow> fluxSink) -> {
			try {
				XmlParser xmlParser = new XmlParser();
				xmlParser.parse(inputStream, LineItemRow.class, new XmlEventListener<LineItemRow>() {

					@Override
					public void element(LineItemRow element) {
						fluxSink.next(element);
						try {
							Thread.sleep(5_000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void completed() {
						fluxSink.complete();
					}
				});
			} catch (XMLStreamException xmlStreamException) {
				fluxSink.error(xmlStreamException);
			}

		});

		return flux;
	}

}
