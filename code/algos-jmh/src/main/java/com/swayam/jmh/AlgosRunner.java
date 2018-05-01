package com.swayam.jmh;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import com.swayam.jmh.algos.IntegerArrayMatcherSimpleStringImplBenchmark;
import com.swayam.jmh.algos.IntegerArrayMatcherTrieImplBenchmark;

public class AlgosRunner {

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(IntegerArrayMatcherSimpleStringImplBenchmark.class.getSimpleName())
				.include(IntegerArrayMatcherTrieImplBenchmark.class.getSimpleName()).warmupIterations(5)
				.measurementIterations(5).forks(3).build();

		new Runner(opt).run();
	}
}
