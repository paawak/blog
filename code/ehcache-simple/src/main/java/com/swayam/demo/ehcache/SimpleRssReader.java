package com.swayam.demo.ehcache;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import com.swayam.demo.ehcache.rss.Channel;
import com.swayam.demo.ehcache.rss.Item;
import com.swayam.demo.ehcache.rss.Rss;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public class SimpleRssReader implements RssReader {

    private final String rssUrl;

    public SimpleRssReader(String rssUrl) {
	this.rssUrl = rssUrl;
    }

    @Override
    public List<Item> getRssFeed() {
	XStream xstream = new XStream(new StaxDriver());
	xstream.processAnnotations(Rss.class);
	xstream.omitField(Channel.class, "link");
	xstream.omitField(Channel.class, "image");
	xstream.omitField(Item.class, "guid");
	xstream.omitField(Item.class, "pubDate");
	xstream.omitField(Item.class, "thumbnail");

	Rss rss;
	try {
	    rss = (Rss) xstream.fromXML(new URL(rssUrl).openStream());
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}

	return rss.getChannel().getItems();
    }

}
