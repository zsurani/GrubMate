package com.usc.zsurani.grubmate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {

    private String name;
    private float rating;
    private int numRatings;
    private String[] reviews;
    // pic
    // private Post[] posts;

    private ListView postList;
    private TextView textRating;
    private TextView textNumRatings;
    private TextView textName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        postList = (ListView) findViewById(R.id.list_posts);
        textRating = (TextView) findViewById(R.id.label_ratings);
        textNumRatings = (TextView) findViewById(R.id.label_num_ratings);
        textName = (TextView) findViewById(R.id.label_name);


        // TODO create custom adapter to put post info into rows on list view
    }
}
