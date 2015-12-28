package com.swayam.demo.xml;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.transform.TransformerException;

import org.junit.Test;

public class XmlPrettyPrinterTest {

    @Test
    public void testTransform() throws TransformerException, IOException {
	InputStream xmlUnformattedInput = XmlPrettyPrinterTest.class.getResourceAsStream("/testData/unformatted-no-spaces.xml");
	XmlPrettyPrinter testClass = new XmlPrettyPrinter();
	testClass.transform(xmlUnformattedInput, System.out);
    }

}
