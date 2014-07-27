package com.swayam.demo.ehcache.rss;

import java.io.Serializable;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("channel")
public class Channel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title;

    private String description;
    private String language;
    private String lastBuildDate;
    private String copyright;
    private String ttl;

    @XStreamImplicit
    private List<Item> items;

    public List<Item> getItems() {
	return items;
    }

    public void setItems(List<Item> items) {
	this.items = items;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public String getLanguage() {
	return language;
    }

    public void setLanguage(String language) {
	this.language = language;
    }

    public String getLastBuildDate() {
	return lastBuildDate;
    }

    public void setLastBuildDate(String lastBuildDate) {
	this.lastBuildDate = lastBuildDate;
    }

    public String getCopyright() {
	return copyright;
    }

    public void setCopyright(String copyright) {
	this.copyright = copyright;
    }

    public String getTtl() {
	return ttl;
    }

    public void setTtl(String ttl) {
	this.ttl = ttl;
    }

}
