package com.swayam.demo.bigdata.spark.simple;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.KeyValueGroupedDataset;
import org.apache.spark.sql.SparkSession;

import scala.Tuple2;

public class HelloSparkAppWithDataset {
	public static void main(String[] args) {

		SparkSession spark = SparkSession.builder().appName(HelloSparkAppWithDataset.class.getSimpleName())
				.getOrCreate();

		Dataset<String> logData = spark.read()
				.textFile("hdfs://localhost:9000/user/paawak/sparkx/apache.access.log.PROJECT")
				.map((String singleLogLine) -> {
					LogLineParser logLineParser = new LogLineParser();
					return logLineParser.extractErrorCode(singleLogLine);
				}, Encoders.STRING()).filter((String errorCode) -> {
					return !errorCode.equals("200");
				});

		KeyValueGroupedDataset<String, String> errorCodeGroupedBy = logData.groupByKey((String errorCode) -> {
			return errorCode;
		}, Encoders.STRING());

		Dataset<Tuple2<String, Long>> groupedByDataset = errorCodeGroupedBy
				.mapGroups((String errorCode, Iterator<String> errorCodeList) -> {
					return new Tuple2<String, Long>(errorCode, StreamSupport
							.stream(Spliterators.spliteratorUnknownSize(errorCodeList, Spliterator.CONCURRENT), true)
							.count());
				}, Encoders.tuple(Encoders.STRING(), Encoders.LONG()));

		groupedByDataset.show();

	}
}
