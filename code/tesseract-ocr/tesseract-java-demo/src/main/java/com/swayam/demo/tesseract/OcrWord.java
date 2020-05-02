package com.swayam.demo.tesseract;

public class OcrWord {

    public final int x1;
    public final int y1;
    public final int x2;
    public final int y2;
    public final float confidence;
    public final String text;
    public final int sequenceNumber;

    public OcrWord(int x1, int y1, int x2, int y2, float confidence, String text, int sequenceNumber) {
	this.x1 = x1;
	this.y1 = y1;
	this.x2 = x2;
	this.y2 = y2;
	this.confidence = confidence;
	this.text = text;
	this.sequenceNumber = sequenceNumber;
    }

    @Override
    public String toString() {
	return "OcrWord [x1=" + x1 + ", y1=" + y1 + ", x2=" + x2 + ", y2=" + y2 + ", confidence=" + confidence + ", text=" + text + ", sequenceNumber=" + sequenceNumber + "]";
    }

}
