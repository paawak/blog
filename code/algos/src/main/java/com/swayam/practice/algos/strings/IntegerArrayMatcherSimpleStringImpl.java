package com.swayam.practice.algos.strings;

public class IntegerArrayMatcherSimpleStringImpl implements IntegerArrayMatcher {

	private static final String SEPARATOR = "_";

	private final String tokensAsString;

	public IntegerArrayMatcherSimpleStringImpl(int[] tokens) {
		tokensAsString = createTokenString(tokens);
	}

	@Override
	public boolean contains(int needle) {
		return tokensAsString.contains(SEPARATOR + needle + SEPARATOR);
	}

	private String createTokenString(int[] tokens) {
		StringBuilder sb = new StringBuilder();
		sb.append(SEPARATOR);
		for (int token : tokens) {
			sb.append(token).append(SEPARATOR);
		}
		return sb.toString();
	}

}
