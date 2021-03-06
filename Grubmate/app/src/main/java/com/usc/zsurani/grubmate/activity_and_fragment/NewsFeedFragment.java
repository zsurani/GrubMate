package com.usc.zsurani.grubmate.activity_and_fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.usc.zsurani.grubmate.MainActivity;
import com.usc.zsurani.grubmate.R;
import com.usc.zsurani.grubmate.adapters.PostAdapter;
import com.usc.zsurani.grubmate.base_classes.Post;
import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.PostRepo;

import java.util.ArrayList;
import java.util.HashSet;
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
    private Button sortButton;
    private HashSet<String> sorts = new HashSet<String>();
    String sortChecks = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_news_feed, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View v = getView();

        List<Post> postList = getPostList();

        adapter = new PostAdapter(getApplicationContext(), R.layout.layout_post_row, postList, getActivity());
        listFeed = (ListView) v.findViewById(R.id.list_feed);
        listFeed.setAdapter(adapter);

//        if (getPostList().size() > 0) {
//            ((TextView) v.findViewById(R.id.text_empty_feed)).setVisibility(View.GONE);
//        } else {
//            Log.d("FEED @!", "SIZE IS : " + getPostList().size());
//        }

        final EditText searchParameter = (EditText)getView().findViewById(R.id.search_text);

        searchButton = (Button) v.findViewById(R.id.button_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).goToFragment(11, searchParameter.getText().toString());
            }
        });

        sortButton = (Button) v.findViewById(R.id.button_sort);
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).goToFragment(3, 0, 0);
            }
        });
    }

    private List<Post> getPostList() {

        List<Post> postList = new ArrayList<>();

        PostRepo repo = new PostRepo(getApplicationContext());
        postList = repo.getPosts();

        return postList;
    }
}


