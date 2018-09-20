package com.example.serega.readerrss_feed.rss_model;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.List;

@Element(name = "channel")
public class Channel {

    @ElementList(inline = true)
    List<Item> item;

    public List<Item> getItem() {
        return item;
    }
}
