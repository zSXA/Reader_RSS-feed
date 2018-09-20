package com.example.serega.readerrss_feed.rss_model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

@Element
public class Enclosure {

    @Attribute
    String url;

    public String getUrl() {
        return url;
    }
}
