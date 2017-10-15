package com.usc.zsurani.grubmate;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.Profile;

/**
 * Created by Madison on 10/15/17.
 */

public class NewsFeedFragment extends Fragment{
    private String [] posts;
    private ListView listFeed;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_news_feed, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        //need to fix the null pointed exceptions
        super.onActivityCreated(savedInstanceState);
        View v = getView();

        listFeed = (ListView) v.findViewById(R.id.list_newsfeed);

        // TODO add adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.layout_post_row, R.id.listText, posts);
        listFeed.setAdapter(adapter);

        // TODO add Options menu to access other pages
    }

}


