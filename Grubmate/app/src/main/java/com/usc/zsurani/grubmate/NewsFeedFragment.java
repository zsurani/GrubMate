package com.usc.zsurani.grubmate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.Profile;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Madison on 10/15/17.
 */

public class NewsFeedFragment extends Fragment{

    private ListView listFeed;
    private PostAdapter adapter;
    private EditText searchParameter;
    private Button searchButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_news_feed, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View v = getView();

        adapter = new PostAdapter(getApplicationContext(), R.layout.layout_post_row, getPostList());
        listFeed = (ListView) v.findViewById(R.id.list_feed);
        listFeed.setAdapter(adapter);

        final EditText searchParameter = (EditText)getView().findViewById(R.id.search_text);

        searchButton = (Button) v.findViewById(R.id.button_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SearchResultsActivity.class);
                i.putExtra ( "SearchParam", searchParameter.getText().toString() );
                startActivity(i);
            }
        });
        // TODO add Options menu to access other pages
    }

    private List<Post> getPostList() {

        List<Post> postList = new ArrayList<>();

        PostRepo repo = new PostRepo(getApplicationContext());
        postList = repo.getPosts();

        return postList;
    }
}


