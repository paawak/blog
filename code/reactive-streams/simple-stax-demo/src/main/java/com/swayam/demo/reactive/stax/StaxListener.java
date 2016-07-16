package com.swayam.demo.reactive.stax;

public interface StaxListener<T> {

    void newElement(T newElement);

    void endOfDocument();

}
