package com.swayam.demo.bigdata.spark.simple;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.swayam.demo.bigdata.spark.simple.LogLineParser;

public class LogLineParserTest {

	@Test
	public void testExtractErrorCode_1() {
		// given

		LogLineParser testClass = new LogLineParser();
		String logLine = "in24.inetnebr.com - - [01/Aug/1995:00:00:01 -0200] \"GET /shuttle/200/sts-68/news/sts-68-mcc-05.txt HTTP/1.0\" 200 1839";

		// when
		String result = testClass.extractErrorCode(logLine);

		// then
		assertEquals("200", result);
	}

	@Test
	public void testExtractErrorCode_2() {
		// given

		LogLineParser testClass = new LogLineParser();
		String logLine = "uplherc.upl.com - - [01/Aug/1995:00:00:14 -0400] \"GET /images/NASA-logosmall.gif HTTP/1.0\" 304 0";

		// when
		String result = testClass.extractErrorCode(logLine);

		// then
		assertEquals("304", result);
	}

}
