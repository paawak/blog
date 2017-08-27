package com.swayam.demo.bigdata.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class HelloSparkApp {
	public static void main(String[] args) {
		// create Spark context with Spark configuration
		try (JavaSparkContext sparkContext = new JavaSparkContext(
				new SparkConf().setAppName(HelloSparkApp.class.getSimpleName()));) {

			// read in text file and split each document into words
			JavaRDD<String> logRdd = sparkContext
					.textFile("hdfs://localhost:9000/user/paawak/sparkx/apache.access.log.PROJECT")
					.map((String singleLogLine) -> {
						// sample log line:
						// in24.inetnebr.com - - [01/Aug/1995:00:00:01 -0400]
						// "GET
						// /shuttle/missions/sts-68/news/sts-68-mcc-05.txt
						// HTTP/1.0" 200
						// 1839
						return singleLogLine.split("\\-")[0].trim();
					}).distinct();

			System.out.println(logRdd.collect());

		}
	}
}
