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
    public void testInsertNotification() throws Exception {
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


}
