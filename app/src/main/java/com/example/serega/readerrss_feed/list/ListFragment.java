package com.example.serega.readerrss_feed.list;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annimon.stream.Stream;

import java.util.Collections;
import java.util.Date;
import java.util.TreeMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import com.example.serega.readerrss_feed.R;
import com.example.serega.readerrss_feed.network.RssService;
import com.example.serega.readerrss_feed.rss_model.Item;
import com.example.serega.readerrss_feed.rss_model.Rss;
import retrofit.RestAdapter;
import retrofit.converter.SimpleXMLConverter;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String LOG_TAG = "ListFragment";
    @InjectView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private TreeMap<Date, Item> mFeedItems;
    private RssAdapter mAdapter;
    String rssStringFragment;
    private SwipeRefreshLayout refreshLayout;

    public static ListFragment newInstance(String rssString) {
        ListFragment fragment = new ListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("rssStringFragment", rssString);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mFeedItems = new TreeMap<>(Collections.reverseOrder());
        mAdapter = new RssAdapter();
        rssStringFragment = this.getArguments().getString("rssStringFragment");

        RestAdapter lentaAdapter = new RestAdapter.Builder()
                .setEndpoint(rssStringFragment)
                .setConverter(new SimpleXMLConverter(false))
                .build();
        RssService lentaService = lentaAdapter.create(RssService.class);

        Observable.merge(
                lentaService.getItems(),
                lentaService.getItems())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Rss>() {
                    @Override
                    public void onCompleted() {
                        mAdapter.putValues(mFeedItems);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(MainActivity.LOG_TAG, "some error happened " + e.getMessage());
                    }

                    @Override
                    public void onNext(Rss rss) {
                        Stream.of(rss.getChannel().getItem())
                                .forEach(item -> mFeedItems.put(item.getDate(), item));
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.inject(this, view);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(getResources()
                .getInteger(R.integer.num_of_columns), StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        Log.d(LOG_TAG, "onCreateView");
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        refreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //Останавливаем обновление:
                refreshLayout.setRefreshing(false);
                Fragment selectFragment = com.example.serega.readerrss_feed.list.ListFragment.newInstance(rssStringFragment);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.message, selectFragment);
                transaction.commit();


            }
        }, 3000);
    }
}