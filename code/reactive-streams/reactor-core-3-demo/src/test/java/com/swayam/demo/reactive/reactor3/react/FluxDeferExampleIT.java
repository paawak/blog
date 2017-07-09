package com.swayam.demo.reactive.reactor3.react;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.zip.GZIPInputStream;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.swayam.demo.reactive.reactor3.model.LineItemRow;

import reactor.core.publisher.Flux;

public class FluxDeferExampleIT {

	private static final Logger LOGGER = LoggerFactory.getLogger(FluxDeferExampleIT.class);

	@Test
	public void testParse() throws IOException, InterruptedException {

		CountDownLatch countDownLatch = new CountDownLatch(1);

		FluxDeferExample xmlParser = new FluxDeferExample();
		Flux<LineItemRow> flux = xmlParser.parse(new GZIPInputStream(
				FluxDeferExampleIT.class.getResourceAsStream("/datasets/xml/www.cs.washington.edu/lineitem.xml.gz")));

		flux.subscribe((LineItemRow lineItemRow) -> {
			LOGGER.info("RAW event: {}", lineItemRow);
		}, null, () -> {
			countDownLatch.countDown();
		});

		countDownLatch.await();

	}

}
