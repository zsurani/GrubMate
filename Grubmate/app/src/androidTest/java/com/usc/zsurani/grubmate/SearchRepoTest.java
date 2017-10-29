package com.usc.zsurani.grubmate;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by Madison on 10/26/17.
 */
@RunWith(AndroidJUnit4.class)
public class SearchRepoTest {
    //local global variables
    Context appContext;
    DatabaseHandler dbHandler;
    SQLiteDatabase db;
    Search s;
    Post p;
    Post p2;
    PostRepo pr;
    @Before
    public void setup() {
        appContext = InstrumentationRegistry.getTargetContext();
        dbHandler = new DatabaseHandler(appContext);
        db = dbHandler.getReadableDatabase();

        //initalizing the NotificationsRepo
        s = new Search(appContext);
        pr = new PostRepo(appContext);

        //initalizing the Noticition we will use to test
    }
    @Test
    public void testSearch() throws Exception {
        p = new Post();
        p.active_status="True";
        Set<String>groupsInPost = new HashSet<String>();
        groupsInPost.add("group1");
        p.groups = groupsInPost;
        p.beginTime = "4";
        p.categories = "Mexican, American";
        p.description = "Bakery";
        p.endTime="5";
        p.food="Burrito";
        p.groupString="group1";
        p.homemade="true";
        p.location="Parkside";
        p.num_requests = "0";
        pr.insert(p);

        p2 = new Post();
        p2.active_status="False";
        Set<String>groupsInPost2 = new HashSet<String>();
        groupsInPost.add("group2");
        p2.groups = groupsInPost2;
        p2.beginTime = "6";
        p2.categories = "Bakery, Indian";
        p2.description = "Samosas";
        p2.endTime="7";
        p2.food="Samosa";
        p2.groupString="group2";
        p2.homemade="false";
        p2.location="Webb";
        p2.num_requests = "3";
        pr.insert(p2);

        List<Post> results= s.searchForPost("Samosa");
        assertEquals("Samosas", results.get(0).getDescription());
    }
}
