package com.swayam.demo.reactive.reactor3.react;

import java.io.InputStream;

import com.swayam.demo.reactive.reactor3.model.LineItemRow;

import reactor.core.publisher.Flux;

public interface ReactiveXmlParser {

	Flux<LineItemRow> parse(InputStream inputStream);

}
