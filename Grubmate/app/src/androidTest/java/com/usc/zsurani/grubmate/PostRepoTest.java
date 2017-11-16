package com.usc.zsurani.grubmate;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.usc.zsurani.grubmate.base_classes.Post;
import com.usc.zsurani.grubmate.base_classes.User;
import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.PostRepo;
import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.UserRepo;
import com.usc.zsurani.grubmate.databases.DatabaseHandler;

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
    PostRepo pr;
    @Before
    public void setup() {
        appContext = InstrumentationRegistry.getTargetContext();
        dbHandler = new DatabaseHandler(appContext);
        db = dbHandler.getReadableDatabase();
        pr = new PostRepo(appContext);

        dbHandler.delete(db);

    }
    @Test
    public void testInsertPost() throws Exception {
        Post p = new Post();
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
        Post p = new Post();
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
        p1.setId(1);

        pr.update(p1);
        Cursor c = db.rawQuery("SELECT * FROM " + Post.TABLE
                + " WHERE " + Post.KEY_id + " = 1", null);

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
        UserRepo ur = new UserRepo(appContext);
        User u = new User();
        u.facebookUniqueIdentifier =  "12345";
        u.name = "TestUser3";
        ur.insert(u);

        assertEquals("true", "" + pr.newUser("56"));
        assertEquals("false", "" + pr.newUser("12345"));
    }

    @Test
    public void testGetPost() throws Exception {
        Post p = new Post();
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
        Post result = pr.getPost(1);
        assertEquals(result.description, "food");
    }


    @Test
    public void testUpdatePostDescription() throws Exception {
        Post p = new Post();
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
        pr.updateDescription("1", "yummy food");
        Log.d("DEBUG", "" + pr.getPost(1).description);
        assertEquals(pr.getPost(1).description, "yummy food");
    }

    @Test
    public void testAddCategoryPost() throws Exception {
        Post p = new Post();
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

        pr.addNewCategory("1", "Indian");
        assertEquals(pr.getPost(1).categories, "Mexican, American,Indian");
    }




}