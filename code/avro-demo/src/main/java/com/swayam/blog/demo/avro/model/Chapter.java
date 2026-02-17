package com.swayam.blog.demo.avro.model;

import java.util.Set;

import lombok.Data;

@Data
public class Chapter {

    private Long id;

    private String title;

    private String plotSummary;

    private Set<Section> sections;

}
