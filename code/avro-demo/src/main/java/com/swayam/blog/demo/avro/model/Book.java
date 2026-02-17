package com.swayam.blog.demo.avro.model;

import java.util.Set;

import lombok.Data;

@Data
public class Book {

    private Long id;

    private String title;

    private Author author;

    private Set<Chapter> chapters;

}
