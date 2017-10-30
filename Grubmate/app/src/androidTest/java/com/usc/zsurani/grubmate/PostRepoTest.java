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
public class PostRepoTest {
    //local global variables
    Context appContext;
    DatabaseHandler dbHandler;
    SQLiteDatabase db;
    Post p;
    PostRepo pr;
    @Before
    public void setup() {
        appContext = InstrumentationRegistry.getTargetContext();
        dbHandler = new DatabaseHandler(appContext);
        db = dbHandler.getReadableDatabase();

        //initalizing the NotificationsRepo
        pr = new PostRepo(appContext);

        //initalizing the Noticition we will use to test
    }
    @Test
    public void testInsertPost() throws Exception {
        p = new Post();
        p.setActive_status("True");
        Set<String>groupsInPost = new HashSet<String>();
        groupsInPost.add("group1");
        p.setGroups(groupsInPost);
        p.setBeginTime("4");
        p.setCategories("Mexican, American");
        p.setDescription("food");
        p.setEndTime("5");
        p.setFood("Burrito");
        p.setGroupString("group1");
        p.setHomemade("true");
        p.setLocation("Parkside");
        p.setNum_requests("0");
        pr.insert(p);

        Cursor c = db.rawQuery("SELECT * FROM " + Post.TABLE
                + " WHERE " + Post.KEY_food + " like '%Burrito%'", null);

        String description = "";
        if (c.moveToFirst()) {
            do {
                description = c.getString(c.getColumnIndex(Post.KEY_description));
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        assertEquals(description, "food");
    }

    @Test
    public void testUpdatePost() throws Exception {
        Post p1 = new Post();
        p1.setActive_status("True");
        Set<String>groupsInPost1 = new HashSet<String>();
        groupsInPost1.add("group1");
        p1.setGroups(groupsInPost1);
        p1.setBeginTime("4");
        p1.setCategories("Indian, Bakery");
        p1.setDescription("food");
        p1.setEndTime("5");
        p1.setFood("Samosa");
        p1.setGroupString("group1");
        p1.setHomemade("true");
        p1.setLocation("Webb");
        p1.setNum_requests("0");
        p1.setOwner_string("123");
        p1.setId(39);

        pr.update(p1);
        Cursor c = db.rawQuery("SELECT * FROM " + Post.TABLE
                + " WHERE " + Post.KEY_id + " = 39", null);

        String location = "";
        if (c.moveToFirst()) {
            do {
                location = c.getString(c.getColumnIndex(Post.KEY_location));
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        assertEquals(location, "Webb");
    }

    @Test
    public void testNewUserPost() throws Exception {
        Log.d("DEBUG", "" + pr.newUser("56"));
        Log.d("DEBUG", "" + pr.newUser("123"));

        assertEquals("true", "" + pr.newUser("56"));

        assertEquals("false", "" + pr.newUser("123"));
    }

    @Test
    public void testGetPost() throws Exception {
        Post result = pr.getPost(39);
        assertEquals(result.description, "food");
    }


    @Test
    public void testUpdatePostDescription() throws Exception {
        pr.updateDescription("39", "yummy food");
        Log.d("DEBUG", "" + pr.getPost(39).description);
        assertEquals(pr.getPost(39).description, "yummy food");
    }

    @Test
    public void testAddCategoryPost() throws Exception {
        pr.addNewCategory("39", "Mexican");
        Log.d("DEBUG", "" + pr.getPost(39).description);
        assertEquals(pr.getPost(39).categories, "Indian, Bakery,Mexican");
    }




}