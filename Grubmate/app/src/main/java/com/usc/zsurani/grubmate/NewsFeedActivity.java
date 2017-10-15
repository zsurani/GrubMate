package com.usc.zsurani.grubmate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class NewsFeedActivity extends AppCompatActivity {

    private String [] posts;
    private ListView listFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        listFeed = (ListView) findViewById(R.id.list_newsfeed);

        // TODO list & adapter
        ArrayList<Post> posts = new ArrayList<Post>();
        PostAdapter adapter = new PostAdapter(getApplicationContext(), R.layout.layout_post_row, posts);
        listFeed.setAdapter(adapter);

        // TODO add Options menu to access other pages

    }


}
