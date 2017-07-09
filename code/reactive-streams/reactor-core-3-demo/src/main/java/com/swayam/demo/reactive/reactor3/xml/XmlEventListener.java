package com.swayam.demo.reactive.reactor3.xml;

public interface XmlEventListener<T> {

	void element(T element);

	void completed();

}
