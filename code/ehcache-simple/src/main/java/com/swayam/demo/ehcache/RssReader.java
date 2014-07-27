package com.swayam.demo.ehcache;

import java.util.List;

import com.swayam.demo.ehcache.rss.Item;

public interface RssReader {

    List<Item> getRssFeed();

}
