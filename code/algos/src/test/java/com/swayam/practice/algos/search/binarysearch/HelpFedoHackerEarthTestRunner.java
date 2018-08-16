package com.swayam.practice.algos.search.binarysearch;

import java.io.IOException;
import java.io.InputStream;

public class HelpFedoHackerEarthTestRunner {

    private static final String TEST_FILE_PATH_PREFIX = "/com/swayam/practice/algos/search/binarysearch/HelpFedoHackerEarth/";
    private static final int TEST_COUNT = 11;

    public static void main(String[] args) throws IOException {

	for (int count = 1; count <= TEST_COUNT; count++) {
	    InputStream inputStream = HelpFedoHackerEarthTestRunner.class.getResourceAsStream(TEST_FILE_PATH_PREFIX + count + ".txt");
	    System.setIn(inputStream);
	    HelpFedoHackerEarth.main(null);
	}

    }

}
