package com.swayam.demo.bigdata.spark.simple;

public class LogLineParser {

	public String extractErrorCode(String logLine) {
		String[] tokensBySpace = logLine.split("\\s");
		return tokensBySpace[tokensBySpace.length - 2];
	}

}
