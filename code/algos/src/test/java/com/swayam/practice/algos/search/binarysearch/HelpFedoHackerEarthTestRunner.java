package com.swayam.practice.algos.search.binarysearch;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class HelpFedoHackerEarthTestRunner {

    private static final String TEST_FILE_PATH_PREFIX = "/com/swayam/practice/algos/search/binarysearch/HelpFedoHackerEarth/";
    private static final int TEST_COUNT = 11;

    public static void main(String[] args) throws IOException {

	ByteArrayOutputStream bos = new ByteArrayOutputStream();
	PrintStream out = new PrintStream(bos);
	System.setOut(out);

	for (int count = 1; count <= TEST_COUNT; count++) {
	    InputStream inputStream = HelpFedoHackerEarthTestRunner.class.getResourceAsStream(TEST_FILE_PATH_PREFIX + count + ".txt");
	    System.setIn(inputStream);

	    HelpFedoHackerEarth.main(null);
	}

	out.flush();

	String allOutput = new String(bos.toByteArray());

	assertEquals(Files.readAllLines(Paths.get(HelpFedoHackerEarthTestRunner.class.getResource(TEST_FILE_PATH_PREFIX + "expected_output.txt").getPath())), Arrays.asList(allOutput.split("\n")));

    }

}
