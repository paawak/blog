package com.swayam.demo.json.polymorphic.withannotation;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "type")
public interface Animal {

    int getLegs();

    String getSpecies();

    String getCommonName();

}
