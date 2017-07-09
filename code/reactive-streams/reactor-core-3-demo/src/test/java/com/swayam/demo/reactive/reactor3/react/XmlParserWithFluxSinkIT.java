package com.swayam.demo.reactive.reactor3.react;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.zip.GZIPInputStream;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.swayam.demo.reactive.reactor3.model.LineItemRow;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class XmlParserWithFluxSinkIT {

	private static final Logger LOGGER = LoggerFactory.getLogger(XmlParserWithFluxSinkIT.class);

	@Test
	public void testParse() throws IOException, InterruptedException {

		CountDownLatch countDownLatch = new CountDownLatch(1);

		XmlParserWithFluxSink xmlParser = new XmlParserWithFluxSink(countDownLatch);
		Flux<LineItemRow> flux = xmlParser.parse(new GZIPInputStream(
				XmlParserWithFluxSinkIT.class.getResourceAsStream("/datasets/xml/www.cs.washington.edu/lineitem.xml.gz")));

		if (true) {
			flux.subscribe((LineItemRow lineItemRow) -> {
				LOGGER.info("RAW event: {}", lineItemRow);
			});
		} else {

			Mono<Map<Integer, List<LineItemRow>>> groupedByData = flux
					.collect(Collectors.groupingBy((LineItemRow row) -> {
						LOGGER.debug("grouping by: {}", row);
						return row.getOrderKey();
					}));

			groupedByData.subscribe((Map<Integer, List<LineItemRow>> next) -> {
				next.entrySet().parallelStream().map((Entry<Integer, List<LineItemRow>> entry) -> {
					int orderKey = entry.getKey();
					DoubleStream doubleStream = entry.getValue().parallelStream().mapToDouble((LineItemRow row) -> {
						return row.getExtendedPrice();
					});

					LineItemRow aggregatedRow = new LineItemRow();
					aggregatedRow.setOrderKey(orderKey);
					aggregatedRow.setExtendedPrice((float) doubleStream.sum());

					return aggregatedRow;
				}).forEach((LineItemRow row) -> {
					LOGGER.info("new aggregated event: {}", row);
				});

			});
		}

		countDownLatch.await();

	}

}
