package com.example.serega.readerrss_feed.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.serega.readerrss_feed.R;

public class Message extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Intent intent = getIntent();
        String rssString = intent.getStringExtra("rssString");

        Fragment selectFragment = com.example.serega.readerrss_feed.list.ListFragment.newInstance(rssString);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.message, selectFragment);
        transaction.commit();

    }
}
