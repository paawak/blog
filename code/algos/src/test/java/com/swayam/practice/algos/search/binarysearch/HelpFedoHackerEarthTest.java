package com.swayam.practice.algos.search.binarysearch;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;

public class HelpFedoHackerEarthTest {

    @Test
    public void testLog10() {
	double log = HelpFedoHackerEarth.findLog10(new BigInteger("1234567890"));
	assertEquals(Math.log10(1234567890), log, 0);
	BigInteger antiLog = HelpFedoHackerEarth.findAntiLog10(log);
	assertEquals(new BigInteger("1234567890"), antiLog);
    }

}
