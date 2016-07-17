package com.swayam.demo.reactive.reactor2.react;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.stream.DoubleStream;
import java.util.zip.GZIPInputStream;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.swayam.demo.reactive.reactor2.model.LineItemRow;

import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;

public class XmlParserReact2IT {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlParserReact2IT.class);

    @Test
    public void testParse() throws IOException, InterruptedException {

	CountDownLatch countDownLatch = new CountDownLatch(1);

	XmlParserReact2 xmlParser = new XmlParserReact2(countDownLatch);
	Flux<LineItemRow> flux = xmlParser.parse(new GZIPInputStream(XmlParserReact2IT.class.getResourceAsStream("/datasets/xml/www.cs.washington.edu/lineitem.xml.gz")));

	flux.doOnNext((LineItemRow row) -> {
	    LOGGER.info("new aggregated event: {}", row);
	});

	countDownLatch.await();

	if (true) {
	    return;
	}

	Flux<GroupedFlux<Integer, LineItemRow>> groupedFlux = flux.groupBy((LineItemRow row) -> {
	    LOGGER.info("processing: {}", row);
	    return row.getOrderKey();
	});

	Flux<LineItemRow> aggregatedFlux = groupedFlux.map((GroupedFlux<Integer, LineItemRow> groupedLineItemRows) -> {

	    Flux<Float> extendedPriceAggregated = groupedLineItemRows.map((LineItemRow row) -> {
		return row.getExtendedPrice();
	    });

	    DoubleStream doubleStream = extendedPriceAggregated.toStream().mapToDouble((Float amount) -> {
		return amount;
	    });

	    LineItemRow aggregatedRow = new LineItemRow();

	    aggregatedRow.setOrderKey(groupedLineItemRows.key());
	    aggregatedRow.setExtendedPrice((float) doubleStream.sum());

	    return aggregatedRow;
	});

	aggregatedFlux.doOnNext((LineItemRow row) -> {
	    LOGGER.info("new aggregated event: {}", row);
	});

    }

}
