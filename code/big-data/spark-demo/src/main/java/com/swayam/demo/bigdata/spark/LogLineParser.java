package com.swayam.demo.bigdata.spark;

public class LogLineParser {

	public String extractErrorCode(String logLine) {
		String[] tokensBySpace = logLine.split("\\s");
		return tokensBySpace[tokensBySpace.length - 2];
	}

}
