package com.swayam.demo.tesseract;

import static org.bytedeco.leptonica.global.lept.L_CLONE;
import static org.bytedeco.leptonica.global.lept.boxaGetBox;
import static org.bytedeco.leptonica.global.lept.pixDestroy;
import static org.bytedeco.leptonica.global.lept.pixRead;

import java.nio.IntBuffer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.leptonica.BOX;
import org.bytedeco.leptonica.BOXA;
import org.bytedeco.leptonica.PIX;
import org.bytedeco.leptonica.PIXA;
import org.bytedeco.tesseract.ETEXT_DESC;
import org.bytedeco.tesseract.ResultIterator;
import org.bytedeco.tesseract.TessBaseAPI;
import org.bytedeco.tesseract.global.tesseract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TesseractOcrAnalyser {

    private static final Logger LOGGER = LoggerFactory.getLogger(TesseractOcrAnalyser.class);

    private final String tessDataDirectory;
    private final Path imagePath;
    private final String language;

    public TesseractOcrAnalyser(String tessDataDirectory, Path imagePath, String language) {
	this.tessDataDirectory = tessDataDirectory;
	this.imagePath = imagePath;
	this.language = language;
    }

    public List<OcrWord> extractLinesFromImage() {

	LOGGER.info("Analyzing image file {} with language {} for lines...", imagePath, language);
	List<OcrWord> lines;

	try (TessBaseAPI api = new TessBaseAPI();) {
	    int returnCode = api.Init(tessDataDirectory, language);
	    if (returnCode != 0) {
		throw new RuntimeException("could not initialize tesseract, error code: " + returnCode);
	    }

	    PIX image = pixRead(imagePath.toFile().getAbsolutePath());

	    LOGGER.info("The image has a width of {} and height of {}", image.w(), image.h());

	    api.SetImage(image);

	    BOXA boxes = api.GetComponentImages(tesseract.RIL_TEXTLINE, true, (PIXA) null, (IntBuffer) null);

	    LOGGER.info("Found {} textline image components.", boxes.n());

	    lines = IntStream.range(0, boxes.n()).mapToObj((int lineSequenceNumber) -> {
		BOX box = boxaGetBox(boxes, lineSequenceNumber, L_CLONE);
		api.SetRectangle(box.x(), box.y(), box.w(), box.h());
		BytePointer ocrResult = api.GetUTF8Text();
		String ocrLineText = ocrResult.getString().trim();
		ocrResult.deallocate();
		int confidence = api.MeanTextConf();
		int x1 = box.x();
		int y1 = box.y();
		int width = box.w();
		int height = box.h();

		OcrWord lineTextBox = new OcrWord(x1, y1, x1 + width, y1 + height, confidence, ocrLineText, lineSequenceNumber + 1);
		LOGGER.debug("lineTextBox: {}", lineTextBox);
		return lineTextBox;
	    }).collect(Collectors.toList());

	    api.End();
	    api.close();
	    pixDestroy(image);
	}

	return Collections.unmodifiableList(lines);

    }

    public List<OcrWord> extractWordsFromImage() {
	LOGGER.info("Analyzing image file {} with language {} for words...", imagePath, language);

	List<OcrWord> words = new ArrayList<>();

	try (TessBaseAPI api = new TessBaseAPI();) {
	    int returnCode = api.Init(tessDataDirectory, language);
	    if (returnCode != 0) {
		throw new RuntimeException("could not initialize tesseract, error code: " + returnCode);
	    }

	    PIX image = pixRead(imagePath.toFile().getAbsolutePath());

	    api.SetImage(image);
	    int code = api.Recognize(new ETEXT_DESC());

	    if (code != 0) {
		throw new IllegalArgumentException("could not recognize text");
	    }

	    try (ResultIterator ri = api.GetIterator();) {
		int level = tesseract.RIL_WORD;
		int wordSequenceNumber = 1;
		Supplier<IntPointer> intPointerSupplier = () -> new IntPointer(new int[1]);
		do {
		    BytePointer ocrResult = ri.GetUTF8Text(level);
		    String ocrText = ocrResult.getString().trim();

		    float confidence = ri.Confidence(level);
		    IntPointer x1 = intPointerSupplier.get();
		    IntPointer y1 = intPointerSupplier.get();
		    IntPointer x2 = intPointerSupplier.get();
		    IntPointer y2 = intPointerSupplier.get();
		    boolean foundRectangle = ri.BoundingBox(level, x1, y1, x2, y2);

		    if (!foundRectangle) {
			throw new IllegalArgumentException("Could not find any rectangle here");
		    }

		    OcrWord wordTextBox = new OcrWord(x1.get(), y1.get(), x2.get(), y2.get(), confidence, ocrText, wordSequenceNumber++);
		    LOGGER.info("wordTextBox: {}", wordTextBox);

		    words.add(wordTextBox);

		    x1.deallocate();
		    y1.deallocate();
		    x2.deallocate();
		    y2.deallocate();
		    ocrResult.deallocate();

		} while (ri.Next(level));

		ri.deallocate();
	    }
	    api.End();
	    api.deallocate();
	    pixDestroy(image);
	}

	return Collections.unmodifiableList(words);
    }

}
