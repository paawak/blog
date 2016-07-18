package com.swayam.demo.reactive.rxjava;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;

import javax.xml.stream.XMLStreamException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.swayam.demo.reactive.rxjava.LineItemRow;
import com.swayam.demo.reactive.rxjava.XmlParser;

public class XmlParserIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlParserIT.class);

    @Test
    public void testParse() throws XMLStreamException, IOException, InterruptedException {

	CountDownLatch countDownLatch = new CountDownLatch(1);

	XmlParser<LineItemRow> xmlParser = new XmlParser<LineItemRow>("T", LineItemRow.class, countDownLatch);
	Stream<LineItemRow> stream = xmlParser.parse(new GZIPInputStream(XmlParserIT.class.getResourceAsStream("/datasets/xml/www.cs.washington.edu/lineitem.xml.gz")));

	Map<Integer, List<LineItemRow>> groupedByData = stream.collect(Collectors.groupingBy((LineItemRow row) -> {
	    LOGGER.trace("grouping by: {}", row);
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
