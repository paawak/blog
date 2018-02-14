package com.swayam.practice.algos.dynamicprogramming.misc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * <h1>Maximum difference of zeros and ones in binary string | Set 2 (O(n)
 * time)</h1>
 * 
 * <p>
 * Given a binary string of 0s and 1s. The task is to find the length of
 * substring which is having a maximum difference of number of 0s and number of
 * 1s (number of 0s – number of 1s). In case of all 1s print -1.
 * </p>
 * Examples:
 * 
 * <pre>

Input : S = "11000010001"
Output : 6
From index 2 to index 9, there are 7
0s and 1 1s, so number of 0s - number
of 1s is 6.

Input : S = "1111"
Output : -1
 * </pre>
 * 
 * <a href=
 * "https://www.geeksforgeeks.org/maximum-difference-zeros-ones-binary-string-set-2-time/">https://www.geeksforgeeks.org/maximum-difference-zeros-ones-binary-string-set-2-time/</a>
 * 
 * @author paawak
 *
 */
public class DifferenceIn0And1 {

	private static final int MAX_LENGTH = 3;
	private final Map<String, Integer> countMap = new HashMap<>();
	// private int cacheHitCount = 0;

	public int compute(String binary) {
		countLengthForCombination(binary);
		// System.out.println("cacheHitCount=" + cacheHitCount);
		return countMap.values().parallelStream().mapToInt(value -> value).max().getAsInt();
	}

	private void countLengthForCombination(String binary) {
		// System.out.println(binary);
		if (!binary.contains("0")) {
			countMap.put(binary, -1);
			return;
		}
		if (!binary.contains("1")) {
			countMap.put(binary, 0);
			return;
		}
		if (countMap.containsKey(binary)) {
			// cacheHitCount++;
			return;
		}

		countMap.put(binary, countDiff(binary));

		for (int i = binary.length(); i >= MAX_LENGTH; i--) {
			for (int j = 0; j < binary.length() - MAX_LENGTH; j++) {
				if (i < j + MAX_LENGTH) {
					continue;
				}
				String substring = binary.substring(j, i);
				countLengthForCombination(substring);
			}
		}


	}

	private int countDiff(String binary) {
		int result = 0;

		char[] bins = binary.toCharArray();

		for (int i = 0; i < bins.length; i++) {
			if (bins[i] == '0') {
				result += 1;
			} else if (bins[i] == '1') {
				result -= 1;
			}
		}

		return result;
	}

	/** Geeks for Geeks code */
	public static void main(String[] a) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		br.readLine();
		String input1 = br.readLine();
		String input2 = br.readLine();
		System.out.println(new DifferenceIn0And1().compute(input1));
		System.out.println(new DifferenceIn0And1().compute(input2));

	}

}
