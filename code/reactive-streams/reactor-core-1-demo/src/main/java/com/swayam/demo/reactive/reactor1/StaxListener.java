package com.swayam.demo.reactive.reactor1;

public interface StaxListener<T> {

    void newElement(T newElement);

    void endOfDocument();

}
