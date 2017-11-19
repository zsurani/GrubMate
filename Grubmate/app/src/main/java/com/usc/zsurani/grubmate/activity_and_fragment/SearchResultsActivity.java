package com.usc.zsurani.grubmate.activity_and_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.usc.zsurani.grubmate.R;
import com.usc.zsurani.grubmate.adapters.PostAdapter;
import com.usc.zsurani.grubmate.base_classes.Post;
import com.usc.zsurani.grubmate.base_classes.Search;

import java.util.List;

public class SearchResultsActivity extends AppCompatActivity {
    String searchParam = "";
    private PostAdapter adapter;
    private ListView searchResultList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        Intent intent = getIntent();
        searchParam = intent.getStringExtra("SearchParam");
        Search search = new Search(getApplicationContext());
        List<Post> postsToDisplay= search.searchForPost(searchParam);
//        adapter = new PostAdapter(getApplicationContext(), R.layout.layout_post_row, postsToDisplay);

        searchResultList = (ListView)findViewById(R.id.list_searchresults);
//        searchResultList.setAdapter(adapter);

    }

}
