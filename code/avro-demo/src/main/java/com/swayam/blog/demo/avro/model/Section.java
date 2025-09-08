package com.swayam.blog.demo.avro.model;

import lombok.Data;

@Data
public class Section {

    private Long id;

    private String sectionText;

    private String style;

    private int length;

}
