package com.swayam.practice.algos;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Log10Util {

    public static double findLog10(BigInteger number) {
	String decimalStringValue = number.toString(10);
	int integralPart = decimalStringValue.length() - 1;
	double decimalPart = Math.log10(new BigDecimal(number).divide(BigDecimal.TEN.pow(integralPart)).doubleValue());
	return integralPart + decimalPart;
    }

    public static BigInteger findAntiLog10(double log) {
	int integralPart = (int) Math.floor(log);
	double decimalPart = log - integralPart;
	double decimalPartAntiLog = Math.pow(10, decimalPart);
	BigDecimal antiLog = BigDecimal.TEN.pow(integralPart).multiply(new BigDecimal(decimalPartAntiLog));
	return antiLog.setScale(1, BigDecimal.ROUND_HALF_EVEN).toBigInteger();
    }

}
