package com.usc.zsurani.grubmate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class NewsFeedActivity extends AppCompatActivity {

    private ListView listFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        listFeed = (ListView) findViewById(R.id.list_newsfeed);

        // TODO add adapter

        // TODO add Options menu to access other pages
    }


}
