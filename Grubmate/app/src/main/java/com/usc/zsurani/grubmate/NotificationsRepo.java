package com.usc.zsurani.grubmate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Madison on 10/14/17.
 */

public class NotificationsRepo {
    private DatabaseHandler dbHelper;

    public NotificationsRepo(Context context) {
        dbHelper = new DatabaseHandler(context);
    }

    //inserts a notification into the database
    public int insert(Notifications n) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(n.KEY_id, n.getId());
        values.put(n.KEY_userID, n.getUserId());

        //loops through the list of categories to turn into a string
        List<String> l = n.getCategory();
        String cate = "";
        for(int i = 0; i < l.size(); i++)
        {
            cate += l.get(i) + ", ";
        }
        values.put(n.KEY_category, cate);

        //loops through the list of tags to turn into a string
        Set<String> li = n.getTags();
        String tag = "";
        for(String s : li)
        {
            tag += s + ", ";
        }

        values.put(n.KEY_tags, tag);
        values.put(n.KEY_beginTime, n.getBeginTime());
        values.put(n.KEY_endTime, n.getEndTime());
        values.put(n.KEY_status, n.isActive());
        values.put(n.KEY_name, n.getName());
        values.put(n.KEY_type, n.getType());

        // Inserting Row
        long notification_Id = db.insert(Notifications.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) notification_Id;
    }

    //get list of notifications for the current user
    public List<String> getNotifications(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT " + Notifications.KEY_id +
                " FROM " + Notifications.TABLE + " where " +
                Notifications.KEY_userID + " = " + userId;
        Cursor c = db.rawQuery(selectQuery, null);

        List<String> not = new ArrayList<String>();
        if(c.moveToFirst()) {
            do {
                not.add(c.getString(c.getColumnIndex(Notifications.KEY_id)));
                Log.d("DEBUG", c.getString(c.getColumnIndex(Notifications.KEY_id)));
            } while (c.moveToNext());
        }
        db.close();
        return not;
    }

    //gets the notification class from a Notification ID
    public Notifications getNotification(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT "
                + Notifications.KEY_userID + ", "
                + Notifications.KEY_category + ", "
                + Notifications.KEY_tags + ", "
                + Notifications.KEY_beginTime + ", "
                + Notifications.KEY_endTime + ", "
                + Notifications.KEY_status + ", "
                + Notifications.KEY_name + ", "
                + Notifications.KEY_type +
                " FROM " + Notifications.TABLE + " where " +
                Notifications.KEY_id + " = " + id;
        Cursor c = db.rawQuery(selectQuery, null);
        Notifications n = null;
        Log.d("DEBUG", "before the if statement");
        if(c.moveToFirst()) {
            do {
                String userId = c.getString(c.getColumnIndex(Notifications.KEY_userID));
                String start = c.getString(c.getColumnIndex(Notifications.KEY_beginTime));
                String end = c.getString(c.getColumnIndex(Notifications.KEY_endTime));
                String type = c.getString(c.getColumnIndex(Notifications.KEY_type));
                String status = c.getString(c.getColumnIndex(Notifications.KEY_status));
                String name = c.getString(c.getColumnIndex(Notifications.KEY_name));
                Log.d("DEBUG", "after the assigning to each of the string variables");

                //gets the category as a string, splits by , and then puts into a list
                String cateString = c.getString(c.getColumnIndex(Notifications.KEY_category));
                String[] cateArray = cateString.split(",");
                List<String> cate = Arrays.asList(cateArray);

                //get the tags as a string, splits by , and then puts into a set
                String tagString = c.getString(c.getColumnIndex(Notifications.KEY_tags));
                String[] tagArray = tagString.split(",");
                Set<String> tag = new HashSet<>(Arrays.asList(tagArray));
                n = new Notifications(name, tag, cate, start, end, type, Integer.parseInt(userId));
                n.setActiveStatus(Boolean.parseBoolean(status));

            } while (c.moveToNext());
        }
        return n;
    }



}
