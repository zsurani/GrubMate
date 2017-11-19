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
import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.PostRepo;
import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.UserRepo;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

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
                postList = sortByTime(postList);
            }
            if (sortCheck.contains("Distance")) {
                postList = sortByPopularity(postList);
            }
        }
//        adapter = new PostAdapter(getApplicationContext(), R.layout.layout_post_row, postList);

        sortResultList = (ListView)findViewById(R.id.list_sortresults);
//        sortResultList.setAdapter(adapter);


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
        UserRepo ur = new UserRepo(getApplicationContext());
        return Integer.parseInt(ur.getRating(Integer.toString(p.getProviderID())));
    }

    private int getNumRating(Post p){
        UserRepo ur = new UserRepo(getApplicationContext());
        return Integer.parseInt(ur.getNumRatings(Integer.toString(p.getProviderID())));
    }

    private int getTime(Post p){
        PostRepo pr = new PostRepo(getApplicationContext());
        String formattedDate = new SimpleDateFormat("hhmmss").format(Calendar.getInstance().getTime()) + "0";
        return Math.abs(Integer.parseInt(formattedDate) - Integer.parseInt(pr.getBeginTime(Integer.toString(p.getProviderID()))));
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

