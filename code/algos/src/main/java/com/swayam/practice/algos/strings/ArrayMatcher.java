package com.swayam.practice.algos.strings;

import java.util.List;
import java.util.Set;

public interface ArrayMatcher<T extends Comparable<T>> {

	List<List<T>> split(List<T> originalArray, Set<List<T>> patterns);

}
