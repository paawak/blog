package com.swayam.demo.xml.jaxb;

import java.util.Collections;
import java.util.List;

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
