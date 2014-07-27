package com.swayam.demo.ehcache.rss;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("rss")
public class Rss implements Serializable {

    private static final long serialVersionUID = 1L;

    private Channel channel;

    public Channel getChannel() {
	return channel;
    }

    public void setChannel(Channel channel) {
	this.channel = channel;
    }

}
