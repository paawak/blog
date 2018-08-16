package com.swayam.practice.algos;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;

public class Log10UtilTest {

    @Test
    public void testLog10() {
	double log = Log10Util.findLog10(new BigInteger("1234567890"));
	assertEquals(Math.log10(1234567890), log, 0);
	BigInteger antiLog = Log10Util.findAntiLog10(log);
	assertEquals(new BigInteger("1234567890"), antiLog);
    }

}
