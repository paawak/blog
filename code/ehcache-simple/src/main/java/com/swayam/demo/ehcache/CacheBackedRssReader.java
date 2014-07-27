package com.swayam.demo.ehcache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import com.swayam.demo.ehcache.rss.Item;

public class CacheBackedRssReader implements RssReader {

    private static final int CACHE_PAGE_SIZE = 5;
    private static final String CACHE_ELEMENT_PREFIX = "ItemsPage_";

    private final RssReader rssReader;
    private final Map<Integer, String> pageMap;
    private final Cache cache;

    public CacheBackedRssReader(RssReader rssReader) {
	this.rssReader = rssReader;

	pageMap = new TreeMap<>();

	CacheManager cacheManager = CacheManager.newInstance(CacheBackedRssReader.class.getResource("/my-demo-ehcache.xml"));
	cache = cacheManager.getCache("myCache1");

    }

    private void initCache() {
	List<Item> allItems = rssReader.getRssFeed();
	for (int i = 0, pageCount = 1; i < allItems.size(); i += CACHE_PAGE_SIZE, pageCount++) {
	    int toIndexExclusive = i + CACHE_PAGE_SIZE;
	    if (toIndexExclusive > allItems.size()) {
		toIndexExclusive = allItems.size();
	    }
	    List<Item> subList = new ArrayList<>(allItems.subList(i, toIndexExclusive));
	    String pageName = CACHE_ELEMENT_PREFIX + pageCount;
	    pageMap.put(pageCount, pageName);
	    Element cacheElement = new Element(pageName, subList);
	    cache.put(cacheElement);
	}
    }

    @Override
    public List<Item> getRssFeed() {
	initCache();

	List<Item> allItems = new ArrayList<>();

	for (Integer pageKey : pageMap.keySet()) {
	    Element element = cache.get(pageMap.get(pageKey));
	    @SuppressWarnings("unchecked")
	    List<Item> items = (List<Item>) element.getObjectValue();
	    allItems.addAll(items);
	}

	return allItems;
    }

}
