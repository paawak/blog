package com.swayam.demo.reactive.reactor3.react;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.zip.GZIPInputStream;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.swayam.demo.reactive.reactor3.model.LineItemRow;
import com.swayam.demo.reactive.reactor3.react.impl.FluxCreateExample;
import com.swayam.demo.reactive.reactor3.react.impl.FluxDeferExample;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReactiveXmlParserIT {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReactiveXmlParserIT.class);

	@Test
	public void testFluxCreate() throws IOException, InterruptedException {
		ReactiveXmlParser xmlParser = new FluxCreateExample();
		testSimpleSubscriber(xmlParser);
		testGroupBy(xmlParser);
	}

	@Ignore
	@Test
	public void testFluxDefer() throws IOException, InterruptedException {
		ReactiveXmlParser xmlParser = new FluxDeferExample();
		testSimpleSubscriber(xmlParser);
		testGroupBy(xmlParser);
	}

	private void testSimpleSubscriber(ReactiveXmlParser xmlParser) throws IOException, InterruptedException {

		LOGGER.info("TEST SIMPLE");

		CountDownLatch countDownLatch = new CountDownLatch(1);

		Flux<LineItemRow> flux = xmlParser.parse(new GZIPInputStream(
				ReactiveXmlParserIT.class.getResourceAsStream("/datasets/xml/www.cs.washington.edu/lineitem.xml.gz")));

		flux.subscribe((LineItemRow lineItemRow) -> {
			LOGGER.debug("RAW event: {}", lineItemRow);
		}, null, () -> {
			LOGGER.info("COMPLETED SIMPLE");
			countDownLatch.countDown();
		});

		countDownLatch.await();

	}

	private void testGroupBy(ReactiveXmlParser xmlParser) throws IOException, InterruptedException {

		LOGGER.info("TEST GROUP BY");

		CountDownLatch countDownLatch = new CountDownLatch(1);

		Flux<LineItemRow> flux = xmlParser.parse(new GZIPInputStream(
				ReactiveXmlParserIT.class.getResourceAsStream("/datasets/xml/www.cs.washington.edu/lineitem.xml.gz")));

		Mono<Map<Integer, List<LineItemRow>>> groupedByData = flux.collect(Collectors.groupingBy((LineItemRow row) -> {
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

		}, null, () -> {
			LOGGER.info("COMPLETED GROUP BY");
			countDownLatch.countDown();
		});

		countDownLatch.await();

	}

}
