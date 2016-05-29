package com.swayam.demo.xml.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class ListWrapper<T> {

    private List<T> list;

    public ListWrapper(List<T> list) {
	this.list = list;
    }

    public ListWrapper() {
	this(new ArrayList<>());
    }

    public List<T> getList() {
	return list;
    }

    public void setList(List<T> list) {
	this.list = list;
    }

}
