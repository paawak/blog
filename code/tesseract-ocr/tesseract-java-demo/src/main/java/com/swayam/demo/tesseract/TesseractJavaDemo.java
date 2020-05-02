package com.swayam.demo.tesseract;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TesseractJavaDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(TesseractJavaDemo.class);

    private static final String TESSDATA_DIR = "/home/paawak/kaaj/installs/tesseract/tessdata_best-4.0.0";
    private static final String BENGALI = "ben";
    private static final String ENGLISH = "eng";

    public static void main(String[] args) throws URISyntaxException {
	Path bengaliImage = Paths.get(TesseractJavaDemo.class.getResource("/images/bangla.jpg").toURI());
	printLinesAndWordsFromOcr(BENGALI, bengaliImage);
	Path englishImage = Paths.get(TesseractJavaDemo.class.getResource("/images/english.png").toURI());
	printLinesAndWordsFromOcr(ENGLISH, englishImage);
	Path mixedImage = Paths.get(TesseractJavaDemo.class.getResource("/images/bangla-english-mixed.png").toURI());
	printLinesAndWordsFromOcr(BENGALI + "+" + ENGLISH, mixedImage);
    }

    private static void printLinesAndWordsFromOcr(String language, Path imageFile) {
	TesseractOcrAnalyser analyser = new TesseractOcrAnalyser(TESSDATA_DIR, imageFile, language);
	// detect words
	List<OcrWord> words = analyser.extractWordsFromImage();
	words.forEach(word -> LOGGER.info("word: {}", word));
	// detect lines
	List<OcrWord> lines = analyser.extractLinesFromImage();
	lines.forEach(line -> LOGGER.info("line: {}", line));
    }

}
