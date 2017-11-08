package com.usc.zsurani.grubmate;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class SortResultFragment extends Fragment {

    private static final String ARG_SORT_CHECK = "sort_result.sort_check";

    private String sortCheck;
    private PostAdapter adapter;
    private ListView sortResultList;


    public SortResultFragment() {
        // Required empty public constructor
    }

    public static SortResultFragment newInstance(String param1) {
        SortResultFragment fragment = new SortResultFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SORT_CHECK, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_sort_result, null);

        if (getArguments() != null) {
            sortCheck = getArguments().getString(ARG_SORT_CHECK);
        }

        Search search = new Search(getContext());
        List<Post> postList= search.searchForPost("");

        if (!sortCheck.equals("")) {
            if (sortCheck.contains("Rating")) {
                postList = sortByRating(postList);
            }
            if (sortCheck.contains("Popularity")) {
                postList = sortByPopularity(postList);
            }
            if (sortCheck.contains("Time")) {
                postList = sortByTime(postList);
            }
            if (sortCheck.contains("Distance")) {
                postList = sortByPopularity(postList);
            }
        }
        adapter = new PostAdapter(getContext(), R.layout.layout_post_row, postList);

        sortResultList = (ListView) v.findViewById(R.id.list_sortresults);
        sortResultList.setAdapter(adapter);

        return v;
    }


    private List<Post>  sortByRating(List<Post> oldList){
        Post[] arrayPost = new Post[oldList.size()];
        arrayPost = oldList.toArray(arrayPost);
        return Arrays.asList(mergesortRating(arrayPost));
    }

    private List<Post>  sortByPopularity(List<Post> oldList){
        Post[] arrayPost = new Post[oldList.size()];
        arrayPost = oldList.toArray(arrayPost);
        return Arrays.asList(mergesortPopularity(arrayPost));
    }

    private List<Post>  sortByTime(List<Post> oldList){
        Post[] arrayPost = new Post[oldList.size()];
        arrayPost = oldList.toArray(arrayPost);
        return Arrays.asList(mergesortTime(arrayPost));
    }

    private int getRating(Post p){
        UserRepo ur = new UserRepo(getContext());
        return Integer.parseInt(ur.getRating(Integer.toString(p.getProviderID())));
    }

    private int getNumRating(Post p){
        UserRepo ur = new UserRepo(getContext());
        return Integer.parseInt(ur.getNumRatings(Integer.toString(p.getProviderID())));
    }

    private int getTime(Post p){
        PostRepo pr = new PostRepo(getContext());
        return Integer.parseInt(pr.getBeginTime(Integer.toString(p.getProviderID())));
    }

    private Post[] mergeRating(Post[] a, Post[] b) {
        Post[] c = new Post[a.length + b.length];
        int i = 0, j = 0;
        for (int k = 0; k < c.length; k++) {
            if      (i >= a.length) c[k] = b[j++];
            else if (j >= b.length) c[k] = a[i++];
            else if (getRating(a[i]) <= getRating(b[j]))  c[k] = a[i++];
            else                    c[k] = b[j++];
        }
        return c;
    }

    public Post[] mergesortRating(Post[] input) {
        int N = input.length;
        if (N <= 1) return input;
        Post[] a = new Post[N/2];
        Post[] b = new Post[N - N/2];
        for (int i = 0; i < a.length; i++)
            a[i] = input[i];
        for (int i = 0; i < b.length; i++)
            b[i] = input[i + N/2];
        return mergeRating(mergesortRating(a), mergesortRating(b));
    }

    private Post[] mergePopularity(Post[] a, Post[] b) {
        Post[] c = new Post[a.length + b.length];
        int i = 0, j = 0;
        for (int k = 0; k < c.length; k++) {
            if      (i >= a.length) c[k] = b[j++];
            else if (j >= b.length) c[k] = a[i++];
            else if (getRating(a[i]) <= getRating(b[j]))  c[k] = a[i++];
            else                    c[k] = b[j++];
        }
        return c;
    }

    public Post[] mergesortPopularity(Post[] input) {
        int N = input.length;
        if (N <= 1) return input;
        Post[] a = new Post[N/2];
        Post[] b = new Post[N - N/2];
        for (int i = 0; i < a.length; i++)
            a[i] = input[i];
        for (int i = 0; i < b.length; i++)
            b[i] = input[i + N/2];
        return mergePopularity(mergesortPopularity(a), mergesortPopularity(b));
    }

    private Post[] mergeTime(Post[] a, Post[] b) {
        Post[] c = new Post[a.length + b.length];
        int i = 0, j = 0;
        for (int k = 0; k < c.length; k++) {
            if      (i >= a.length) c[k] = b[j++];
            else if (j >= b.length) c[k] = a[i++];
            else if (getTime(a[i]) <= getTime(b[j]))  c[k] = a[i++];
            else                    c[k] = b[j++];
        }
        return c;
    }

    public Post[] mergesortTime(Post[] input) {
        int N = input.length;
        if (N <= 1) return input;
        Post[] a = new Post[N/2];
        Post[] b = new Post[N - N/2];
        for (int i = 0; i < a.length; i++)
            a[i] = input[i];
        for (int i = 0; i < b.length; i++)
            b[i] = input[i + N/2];
        return mergeTime(mergesortTime(a), mergesortTime(b));
    }

}
