package com.swayam.demo.reactive.stax;

import java.io.IOException;
import java.util.zip.GZIPInputStream;

import javax.xml.stream.XMLStreamException;

import org.junit.Test;

public class XmlParserTest {

    @Test
    public void testParse() throws XMLStreamException, IOException {
	XmlParser xmlParser = new XmlParser();
	xmlParser.parse(new GZIPInputStream(XmlParserTest.class.getResourceAsStream("/datasets/xml/www.cs.washington.edu/lineitem.xml.gz")));
    }

}
