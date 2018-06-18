package com.swayam.demo.web.xls.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.swayam.demo.web.xls.jersey2.service.XlsReader;

public class XlsReaderTest {

	@Test
	public void testRead() throws IOException {
		// given
		List<String> expected = Arrays.asList("A", "B", "C", "D", "11.0", "12.0", "13.0", "14.0", "21.0", "22.0",
				"23.0", "24.0", "31.0", "32.0", "33.0", "34.0", "41.0", "42.0", "43.0", "44.0");

		InputStream inputStream = XlsReaderTest.class.getResourceAsStream("/xls/test-simple.xls");

		XlsReader testClass = new XlsReader();

		// when
		List<String> result = testClass.read(inputStream);

		// then
		assertEquals(expected, result);
	}

}
