package com.swayam.demo;

import java.util.ArrayList;
import java.util.List;

public class BadPrecedentClient {

    private final BadPrecedentService badPrecedentService;

    public BadPrecedentClient(BadPrecedentService badPrecedentService) {
	this.badPrecedentService = badPrecedentService;
    }

    public List<String> getNamesOfActiveItems() {
	List<Item> items = new ArrayList<Item>(10);
	badPrecedentService.setThePassedInList(items);

	List<String> names = new ArrayList<String>(items.size());

	for (Item item : items) {
	    if (item.isActive()) {
		names.add(item.getName());
	    }
	}

	return names;
    }

}
