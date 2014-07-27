package com.swayam.demo.ehcache;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.swayam.demo.ehcache.rss.Item;

public class CacheBackedRssReaderTest {

    @Test
    public void testGetRssFeed() {
	// given
	String bbcNewsRss = "http://feeds.bbci.co.uk/news/rss.xml";
	RssReader simpleRssReader = new SimpleRssReader(bbcNewsRss);
	List<Item> expectedItems = simpleRssReader.getRssFeed();

	CacheBackedRssReader testClass = new CacheBackedRssReader(simpleRssReader);

	// when
	List<Item> result = testClass.getRssFeed();

	// then
	assertEquals(expectedItems, result);
    }

}
