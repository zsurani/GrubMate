package com.usc.zsurani.grubmate.activity_and_fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.usc.zsurani.grubmate.R;
import com.usc.zsurani.grubmate.adapters.PostAdapter;
import com.usc.zsurani.grubmate.base_classes.Post;
import com.usc.zsurani.grubmate.base_classes.Search;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchResultsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchResultsFragment extends Fragment {

    String searchParam = "";
    private PostAdapter adapter;
    private ListView searchResultList;

    private static final String ARG_SEARCH = "search_results.search_string";

    public SearchResultsFragment() {
        // Required empty public constructor
    }

    public static SearchResultsFragment newInstance(String param1) {
        SearchResultsFragment fragment = new SearchResultsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SEARCH, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("SEARCH RESULT", "USING SEARCH RESULT FRAG");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_search_results, null);

        if (getArguments() != null) {
            searchParam = getArguments().getString(ARG_SEARCH);
        }

        Search search = new Search(getContext());
        List<Post> postsToDisplay= search.searchForPost(searchParam);
        adapter = new PostAdapter(getContext(), R.layout.layout_post_row, postsToDisplay, getActivity());

        searchResultList = (ListView) v.findViewById(R.id.list_searchresults);
        searchResultList.setAdapter(adapter);

        return v;
    }

}
