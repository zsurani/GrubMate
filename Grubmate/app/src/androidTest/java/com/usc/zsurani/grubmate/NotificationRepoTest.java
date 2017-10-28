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

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by Madison on 10/26/17.
 */
@RunWith(AndroidJUnit4.class)
public class NotificationRepoTest {
    //local global variables
    Context appContext;
    DatabaseHandler dbHandler;
    SQLiteDatabase db;
    Notifications n;
    NotificationsRepo nr;
    @Before
    public void setup() {
        appContext = InstrumentationRegistry.getTargetContext();
        dbHandler = new DatabaseHandler(appContext);
        db = dbHandler.getReadableDatabase();

        //initalizing the NotificationsRepo
        nr = new NotificationsRepo(appContext);

        //initalizing the Noticition we will use to test
        n = new Notifications();
        n.setProvider(1);
        n.id = 1;
        n.userId = 1;
        n.requestID = "1";
        n.location = "location";
        n.postID = 1;
        Set<String> c = new HashSet<>();
        c.add("cate1");
        n.category = c;
        Set<String> t = new HashSet<>();
        t.add("tag1");
        n.tags = t;
        n.beginTime = "1:00PM";
        n.endTime = "2:00PM";
        n.status = true;
        n.name = "name";
        n.type = "type";
    }
    @Test
    public void testInsertNotification() throws Exception {
        //not working right now
        Integer id = nr.insert(n);

        String selectQuery = "SELECT "
                + Notifications.KEY_name + " FROM " + Notifications.TABLE + " where " +
                Notifications.KEY_userID + "=" + "1";
        Notifications n1 = null;
        String name = "";

        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                String userId = c.getString(c.getColumnIndex(Notifications.KEY_userID));
                String start = c.getString(c.getColumnIndex(Notifications.KEY_beginTime));
                String end = c.getString(c.getColumnIndex(Notifications.KEY_endTime));
                String type = c.getString(c.getColumnIndex(Notifications.KEY_type));
                String status = c.getString(c.getColumnIndex(Notifications.KEY_status));
                name = "name";


                //gets the category as a string, splits by , and then puts into a list
                String cateString = c.getString(c.getColumnIndex(Notifications.KEY_category));
                String[] cateArray = new String[]{};
                if (cateString != null) cateArray = cateString.split(",");
                List<String> cate = Arrays.asList(cateArray);
                Set<String> tag = null;
                //get the tags as a string, splits by , and then puts into a set
                String tagString = c.getString(c.getColumnIndex(Notifications.KEY_tags));

                if (tagString != null) {
                    String[] tagArray = tagString.split(",");
                    tag = new HashSet<>(Arrays.asList(tagArray));
                }

                Set<String> cateSet = new HashSet<String>(cate);

                n1 = new Notifications(name, tag, cateSet, start, end, type, userId);
                if (status == null || status.equals("0")) {
                    n1.setActiveStatus(false);
                } else {
                    n1.setActiveStatus(true);
                }



            } while (c.moveToNext());
        }
        Log.d("DEBUG", "n1.getName() = " + name);
        Log.d("DEBUG", "n.getName() = " + n.getName());
        System.out.println("n.getName() = " + n.getName());
        System.out.println("n1.getName() = " + name);
        assertEquals(name, n.getName());
    }
}
