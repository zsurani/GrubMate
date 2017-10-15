package com.usc.zsurani.grubmate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class NewsFeedActivity extends AppCompatActivity {

    private String [] posts;
    private ListView listFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        listFeed = (ListView) findViewById(R.id.list_newsfeed);

        // TODO add adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.layout_post_row, R.id.listText, posts);
        listFeed.setAdapter(adapter);

        // TODO add Options menu to access other pages
    }



}
