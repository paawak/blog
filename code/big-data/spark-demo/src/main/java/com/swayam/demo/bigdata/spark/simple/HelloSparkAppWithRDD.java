package com.swayam.demo.bigdata.spark.simple;

import java.util.stream.StreamSupport;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import scala.Tuple2;

public class HelloSparkAppWithRDD {
	public static void main(String[] args) {
		try (JavaSparkContext sparkContext = new JavaSparkContext(
				new SparkConf().setAppName(HelloSparkAppWithRDD.class.getSimpleName()));) {

			JavaRDD<String> logRdd = sparkContext
					.textFile("hdfs://localhost:9000/user/paawak/sparkx/apache.access.log.PROJECT")
					.map((String singleLogLine) -> {
						LogLineParser logLineParser = new LogLineParser();
						return logLineParser.extractErrorCode(singleLogLine);
					}).filter((String errorCode) -> {
						return !errorCode.equals("200");
					});

			JavaPairRDD<String, Long> errorCodePair = logRdd.groupBy((String errorCode) -> {
				return errorCode;
			}).mapToPair((Tuple2<String, Iterable<String>> input) -> {
				Tuple2<String, Long> outputTuple = new Tuple2<>(input._1,
						StreamSupport.stream(input._2.spliterator(), true).count());
				return outputTuple;
			});

			errorCodePair.saveAsTextFile("hdfs://localhost:9000/user/paawak/sparkx/out/"
					+ HelloSparkAppWithRDD.class.getSimpleName() + ".out");

		}
	}
}
