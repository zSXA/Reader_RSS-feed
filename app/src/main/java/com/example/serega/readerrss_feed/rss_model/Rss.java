package com.example.serega.readerrss_feed.rss_model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class Rss {

    @Element(name = "channel")
    Channel channel;

    public Channel getChannel() {
        return channel;
    }
}
