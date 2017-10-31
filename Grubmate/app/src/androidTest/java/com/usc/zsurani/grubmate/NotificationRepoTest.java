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
    NotificationsRepo nr;
    @Before
    public void setup() {
        appContext = InstrumentationRegistry.getTargetContext();
        dbHandler = new DatabaseHandler(appContext);
        db = dbHandler.getReadableDatabase();

        nr = new NotificationsRepo(appContext);
        dbHandler.delete(db);
    }
    @Test
    public void testInserttNotification() throws Exception {
        Notifications n= new Notifications();
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
        Integer id = nr.insert(n);

        String selectQuery = "SELECT "
                + Notifications.KEY_name + " FROM " + Notifications.TABLE + " where " +
                Notifications.KEY_userID + "=" + "1";
        Notifications n1 = null;
        String name = "";

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String userId = cursor.getString(cursor.getColumnIndex(Notifications.KEY_userID));
                String start = cursor.getString(cursor.getColumnIndex(Notifications.KEY_beginTime));
                String end = cursor.getString(cursor.getColumnIndex(Notifications.KEY_endTime));
                String type = cursor.getString(cursor.getColumnIndex(Notifications.KEY_type));
                String status = cursor.getString(cursor.getColumnIndex(Notifications.KEY_status));
                name = "name";


                //gets the category as a string, splits by , and then puts into a list
                String cateString = cursor.getString(cursor.getColumnIndex(Notifications.KEY_category));
                String[] cateArray = new String[]{};
                if (cateString != null) cateArray = cateString.split(",");
                List<String> cate = Arrays.asList(cateArray);
                Set<String> tag = null;
                //get the tags as a string, splits by , and then puts into a set
                String tagString = cursor.getString(cursor.getColumnIndex(Notifications.KEY_tags));

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



            } while (cursor.moveToNext());
        }
        assertEquals("name", n.getName());
    }

    @Test
    public void testInsertRequestNotification() throws Exception {
        Notifications n1 = new Notifications();
        n1.postID = 2;
        n1.requesterID = 3;
        n1.userId = 1;
        n1.status = true;
        n1.type = "REQUEST";
        Integer id = nr.insertRequest(n1);

        String selectQuery = "SELECT "
                + Notifications.KEY_requestorID + " FROM " + Notifications.TABLE + " where " +
                Notifications.KEY_userID + "=" + "1";
        String requestorID = "";

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                requestorID = cursor.getString(cursor.getColumnIndex(Notifications.KEY_requestorID));
            } while (cursor.moveToNext());
        }
        assertEquals("3", requestorID);
    }

    @Test
    public void testGetNotificationFromUserId() throws Exception {
        Notifications n= new Notifications();
        n.setProvider(1);
        n.id = 1;
        n.userId = 2;
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
        Integer id = nr.insertRequest(n);
        assertEquals(1, nr.getNotifications(2).size());
    }


    @Test
    public void testGetNotificationFromNotifId() throws Exception {
        Notifications n= new Notifications();
        n.setProvider(1);
        n.id = 1;
        n.userId = 2;
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
        n.type = "REQUEST";
        Integer id = nr.insertRequest(n);
        assertEquals("REQUEST", "" + nr.getNotification(1).type);
    }

    @Test
    public void testGetCategoriesFromNotifId() throws Exception {
        Notifications n= new Notifications();
        n.setProvider(1);
        n.id = 1;
        n.userId = 2;
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
        n.type = "REQUEST";
        Integer id = nr.insert(n);
        assertEquals("cate1", "" + nr.getCategories("0"));
    }

    @Test
    public void testUpdateStatus() throws Exception {
        Notifications n= new Notifications();
        n.setProvider(1);
        n.id = 0;
        n.userId = 2;
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
        n.type = "REQUEST";
        Integer id = nr.insert(n);

        nr.updateStatus("0", "2");
        String selectQuery = "SELECT "
                + Notifications.KEY_status + " FROM " + Notifications.TABLE + " where " +
                Notifications.KEY_id + "=" + "0";
        String status = "";

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                status = cursor.getString(cursor.getColumnIndex(Notifications.KEY_status));
            } while (cursor.moveToNext());
        }
        Log.d("weird", status);
        assertEquals("2", status);
    }

    @Test
    public void testDeleteNotification() throws Exception {
        Notifications n= new Notifications();
        n.setProvider(1);
        n.id = 1;
        n.userId = 2;
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
        n.type = "REQUEST";
        Integer id = nr.insertRequest(n);

        String selectQuery = "SELECT * FROM " + Notifications.TABLE;
        int count = 0;

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                count++;
            } while (cursor.moveToNext());
        }
        assertEquals(1, count);

        nr.deleteNotification("1");

        selectQuery = "SELECT * FROM " + Notifications.TABLE;
        count = 0;

        cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                count++;
            } while (cursor.moveToNext());
        }
        assertEquals(0, count);

    }

}
