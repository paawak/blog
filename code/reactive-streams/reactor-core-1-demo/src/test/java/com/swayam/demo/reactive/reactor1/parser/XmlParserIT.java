package com.swayam.demo.reactive.reactor1.parser;

import java.io.IOException;
import java.util.zip.GZIPInputStream;

import javax.xml.stream.XMLStreamException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.swayam.demo.reactive.reactor1.StaxListener;
import com.swayam.demo.reactive.reactor1.model.LineItemRow;

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

	XmlParser xmlParser = new XmlParser(staxListener);
	xmlParser.parse(new GZIPInputStream(XmlParserIT.class.getResourceAsStream("/datasets/xml/www.cs.washington.edu/lineitem.xml.gz")));
    }

}
