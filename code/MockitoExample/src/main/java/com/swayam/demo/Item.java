package com.swayam.demo;

public class Item {

    private final String name;
    private final boolean active;

    public Item(String name, boolean active) {
	this.name = name;
	this.active = active;
    }

    public String getName() {
	return name;
    }

    public boolean isActive() {
	return active;
    }

}
