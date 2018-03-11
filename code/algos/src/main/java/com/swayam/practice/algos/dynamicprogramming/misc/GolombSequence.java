package com.swayam.practice.algos.dynamicprogramming.misc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * <h1>Golomb sequence</h1>
 * <p>
 * In mathematics, the Golomb sequence is a non-decreasing integer sequence
 * where n-th term is equal to number of times n appears in the sequence.
 * </p>
 * <p>
 * The first few values are<br>
 * 1, 2, 2, 3, 3, 4, 4, 4, 5, 5, 5, ……
 * </p>
 * <h2>Explanation of few terms</h2>
 * <p>
 * Third term is 2, note that three appears 2 times. Second term is 2, note that
 * two appears 2 times. Fourth term is 3, note that four appears 3 times.
 * 
 * Given a positive integer n. The task is to find the first n terms of Golomb
 * sequence.
 * </p>
 * 
 * <p>
 * The recurrence relation to find the nth term of Golomb sequence:<br>
 * <b>a(1) = 1</b><br>
 * <b>a(n + 1) = 1 + a(n + 1 – a(a(n)))</b>
 * </p>
 * 
 * <h2>Examples</h2>
 * <ol>
 * <li>Input : n = 4 :: Output : 1 2 2 3</li>
 * <li>Input : n = 6 :: Output : 1 2 2 3 3 4</li>
 * <li>Input : n = 9 :: Output : 1 2 2 3 3 4 4 4 5</li>
 * <li>Input : n = 11 :: Output : 1 2 2 3 3 4 4 4 5 5 5</li>
 * </ol>
 * 
 * <p>
 * Reference: <a href=
 * "https://www.geeksforgeeks.org/golomb-sequence/">https://www.geeksforgeeks.org/golomb-sequence/</a>
 * </p>
 * 
 * @author paawak
 *
 */
public class GolombSequence {

	private final int maxIndex;

	private final int[] cache;

	public GolombSequence(int maxIndex) {
		this.maxIndex = maxIndex;
		cache = new int[maxIndex + 1];
	}

	public void printSequence() {
		for (int i = 1; i <= maxIndex; i++) {
			System.out.print(getGolombNumber(i) + " ");
		}
	}

	private int getGolombNumber(int index) {

		if (cache[index] != 0) {
			return cache[index];
		}

		if (index == 1) {
			int value = 1;
			cache[index] = value;
			return value;
		}

		int currentValue = 1 + getGolombNumber(index - getGolombNumber(getGolombNumber(index - 1)));

		cache[index] = currentValue;
		return currentValue;

	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int maxIndex = Integer.parseInt(br.readLine());
		GolombSequence golombSequence = new GolombSequence(maxIndex);
		golombSequence.printSequence();
	}

}
