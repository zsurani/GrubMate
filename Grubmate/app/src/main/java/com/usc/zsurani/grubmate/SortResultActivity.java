package com.usc.zsurani.grubmate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class SortResultActivity extends AppCompatActivity {
    String sortCheck = "";
    private PostAdapter adapter;
    private ListView sortResultList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_result);
        Intent intent = getIntent();
        sortCheck = intent.getStringExtra("sortChecks");
        Search search = new Search(getApplicationContext());
        List<Post> postList= search.searchForPost("");

        if (!sortCheck.equals("")) {
            if (sortCheck.contains("Rating")) {
                postList = sortByRating(postList);
            }
            if (sortCheck.contains("Popularity")) {
                postList = sortByPopularity(postList);
            }
            if (sortCheck.contains("Time")) {
            }
            if (sortCheck.contains("Distance")) {
            }
        }
        adapter = new PostAdapter(getApplicationContext(), R.layout.layout_post_row, postList);

        sortResultList = (ListView)findViewById(R.id.list_sortresults);
        sortResultList.setAdapter(adapter);


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

    private int getRating(Post p){
        UserRepo ur = new UserRepo(getApplicationContext());
        return Integer.parseInt(ur.getRating(Integer.toString(p.getProviderID())));
    }

    private int getNumRating(Post p){
        UserRepo ur = new UserRepo(getApplicationContext());
        return Integer.parseInt(ur.getNumRatings(Integer.toString(p.getProviderID())));
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
}

