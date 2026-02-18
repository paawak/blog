package com.swayam.blog.demo.avro.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Author {

	private Date dateOfBirth;

	private Long id;

	@JsonProperty("name")
	private String firstName;

	private String lastName;

}
