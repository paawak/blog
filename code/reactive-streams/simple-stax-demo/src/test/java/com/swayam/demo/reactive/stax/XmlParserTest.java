package com.swayam.demo.reactive.stax;

import javax.xml.stream.XMLStreamException;

import org.junit.Test;

public class XmlParserTest {

    @Test
    public void testParse() throws XMLStreamException {
	XmlParser xmlParser = new XmlParser();
	xmlParser.parse(XmlParserTest.class.getResourceAsStream("/datasets/xml/www.cs.washington.edu/mondial-3.0.xml"));
    }

}
