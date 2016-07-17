package com.swayam.demo.reactive.reactor2.react;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.swayam.demo.reactive.reactor2.model.LineItemRow;

public class XmlParserReact2IT {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlParserReact2IT.class);

    @Test
    public void testParse() throws IOException, InterruptedException {

	CountDownLatch countDownLatch = new CountDownLatch(1);

	XmlParserReact2 xmlParser = new XmlParserReact2(countDownLatch);
	Stream<LineItemRow> flux = xmlParser.parse(new GZIPInputStream(XmlParserReact2IT.class.getResourceAsStream("/datasets/xml/www.cs.washington.edu/lineitem.xml.gz")));

	Map<Integer, List<LineItemRow>> groupedByData = flux.collect(Collectors.groupingBy((LineItemRow row) -> {
	    LOGGER.info("grouping by: {}", row);
	    return row.getOrderKey();
	}));

	groupedByData.entrySet().parallelStream().map((Entry<Integer, List<LineItemRow>> entry) -> {
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

	countDownLatch.await();

    }

}
