package com.swayam.demo.reactive.stax;

import java.io.IOException;
import java.util.zip.GZIPInputStream;

import javax.xml.stream.XMLStreamException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlParserIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlParserIT.class);

    @Test
    public void testParse() throws XMLStreamException, IOException {

	StaxListener<LineItemRow> staxListener = new StaxListener<LineItemRow>() {

	    @Override
	    public void newElement(LineItemRow newElement) {
		LOGGER.info("newElement: {}", newElement);
	    }

	    @Override
	    public void endOfDocument() {
		LOGGER.info("Document ended");
	    }
	};

	XmlParser<LineItemRow> xmlParser = new XmlParser<LineItemRow>(staxListener, "T", LineItemRow.class);
	xmlParser.parse(new GZIPInputStream(XmlParserIT.class.getResourceAsStream("/datasets/xml/www.cs.washington.edu/lineitem.xml.gz")));
    }

}
