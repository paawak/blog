package com.swayam.demo.xml.jaxb;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class ListWrapper<T> {

    private final List<T> list;

    public ListWrapper(List<T> list) {
        this.list = list;
    }

    public ListWrapper() {
        this(Collections.emptyList());
    }

    public List<T> getList() {
        return list;
    }

}
