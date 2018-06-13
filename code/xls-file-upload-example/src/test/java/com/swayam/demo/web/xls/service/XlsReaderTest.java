package com.swayam.demo.web.xls.service;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

public class XlsReaderTest {

	@Test
	public void testRead() throws IOException {
		InputStream inputStream = XlsReaderTest.class.getResourceAsStream("/xls/test-simple.xls");
		XlsReader xlsReader = new XlsReader();
		xlsReader.read(inputStream);
	}

}
