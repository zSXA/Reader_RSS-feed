package com.example.serega.readerrss_feed.network;

import com.example.serega.readerrss_feed.rss_model.Rss;

import retrofit.http.GET;
import rx.Observable;

public interface RssService {
    @GET("/rss")
    Observable<Rss> getItems();
}
