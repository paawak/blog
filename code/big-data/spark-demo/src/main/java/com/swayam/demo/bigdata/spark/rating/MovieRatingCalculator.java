package com.swayam.demo.bigdata.spark.rating;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import scala.Tuple2;

public class MovieRatingCalculator {

	private static final String HDFS_INPUT_OUTPUT_DIRECTORY = "hdfs://localhost:9000/user/paawak/sparkx/";

	public static void main(String[] args) {
		try (JavaSparkContext sparkContext = new JavaSparkContext(
				new SparkConf().setAppName(MovieRatingCalculator.class.getSimpleName()));) {

			UserRatingParser userRatingParser = new UserRatingParser();

			// process the ratings file
			JavaRDD<UserRating> userRatingRdd = sparkContext.textFile(HDFS_INPUT_OUTPUT_DIRECTORY + "ratings.dat")
					.map((String singleUserRatingLine) -> {
						return userRatingParser.parse(singleUserRatingLine);
					});

			// find the average ratings for movies
			JavaPairRDD<Integer, Double> averageMovieRatings = userRatingRdd.groupBy((UserRating userRating) -> {
				return userRating.getMovieId();
			}).mapToPair((Tuple2<Integer, Iterable<UserRating>> input) -> {
				Tuple2<Integer, Double> outputTuple = new Tuple2<>(input._1,
						StreamSupport.stream(input._2.spliterator(), true).mapToInt((UserRating userRating) -> {
							return userRating.getRating();
						}).average().getAsDouble());
				return outputTuple;
			});

			JavaPairRDD<Integer, String> movieMaps = sparkContext.textFile(HDFS_INPUT_OUTPUT_DIRECTORY + "movies.dat")
					.mapToPair((String singleMovieLine) -> {
						String[] tokens = singleMovieLine.split("::");
						return new Tuple2<Integer, String>(Integer.parseInt(tokens[0]), tokens[1]);
					});

			// find the top rated 10 movies
			List<Tuple2<String, Double>> topTenMovies = averageMovieRatings
					.mapToPair((Tuple2<Integer, Double> input) -> {
						return input.swap();
					}).sortByKey(false).mapToPair((Tuple2<Double, Integer> input) -> {
						return input.swap();
					}).take(10).stream().map((Tuple2<Integer, Double> input) -> {
						String movieName = movieMaps.lookup(input._1).get(0);
						return new Tuple2<String, Double>(movieName, input._2);
					}).collect(Collectors.toList());

			System.out.println("**********  Top 10 Movies ***********");

			topTenMovies.forEach((Tuple2<String, Double> movieRating) -> {
				System.out.println(movieRating._1 + "\t:\t" + movieRating._2);
			});

		}
	}
}
