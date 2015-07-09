package com.swayam.demo.algo;

public class FactorialCalculator {

    public int find(int n) {
	if (n < 0) {
	    throw new IllegalArgumentException("Cannot find factorial of a negative no.");
	} else if (n == 0) {
	    return 1;
	} else {
	    return n * find(n - 1);
	}
    }

    public static void main(String[] a) {
	FactorialCalculator factorialCalculator = new FactorialCalculator();
	System.out.println("5!=" + factorialCalculator.find(5));
    }

}
