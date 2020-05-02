package com.swayam.demo.tesseract;

public enum Language {

    BENGALI("ben"), HINDI("hin"), ENGLISH("eng");

    public final String code;

    private Language(String code) {
	this.code = code;
    }

}
