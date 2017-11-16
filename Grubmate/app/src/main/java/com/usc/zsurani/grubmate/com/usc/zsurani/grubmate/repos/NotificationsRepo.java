package com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.usc.zsurani.grubmate.databases.DatabaseHandler;
import com.usc.zsurani.grubmate.base_classes.Notifications;
import com.usc.zsurani.grubmate.base_classes.Post;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.lang.String;

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
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + Notifications.TABLE;
        Cursor c = db.rawQuery(selectQuery, null);
        int nextId = 0;
        if (c.moveToFirst()) {
            nextId = c.getCount();
        }

        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(n.KEY_id, nextId);
        values.put(n.KEY_userID, n.getUserId());

        //loops through the list of categories to turn into a string
        Set<String> l = n.getCategory();
        if(l != null) {
            List<String> list = new ArrayList<String>(l);
            String cate = TextUtils.join(", ", list);
            values.put(n.KEY_category, cate);
        }

        //loops through the list of tags to turn into a string
        Set<String> li = n.getTags();
        if(li != null) {
            String tag = "";
            for (String s : li) {
                tag += s + ",";
            }
            values.put(n.KEY_tags, tag);
        }

        if(n.getBeginTime() != null) {
            values.put(n.KEY_beginTime, n.getBeginTime());
        }
        if(n.getEndTime() != null) {
            values.put(n.KEY_endTime, n.getEndTime());
        }
        values.put(n.KEY_status, n.isActive());
        values.put(n.KEY_name, n.getName());
        values.put(n.KEY_type, n.getType());

        // Inserting Row
        long notification_Id = db.insert(Notifications.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) notification_Id;
    }

    public int insertRequest(Notifications n) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(n.KEY_postID, n.getPostID());
        values.put(n.KEY_requestorID, n.getRequestID());
        values.put(n.KEY_userID, n.getProvider());
        // true means still active; when provider responds, it turns inactive
        values.put(n.KEY_status, "true");
        values.put(n.KEY_type, "REQUEST");

        // Inserting Row
        long notification_Id = db.insert(Notifications.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) notification_Id;
    }

    public int insertReviewRequest(Notifications n) {
        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(n.KEY_postID, n.getPostID());
        values.put(n.KEY_requestorID, n.getRequestID()); //who is being sent the rating
        values.put(n.KEY_userID, n.getProvider()); //who we are rating
        values.put(n.KEY_type, "REVIEW");

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
                String[] cateArray = new String[] {};
                if (cateString != null) cateArray = cateString.split(",");
                List<String> cate = Arrays.asList(cateArray);
                Set<String> tag = null;
                //get the tags as a string, splits by , and then puts into a set
                String tagString = c.getString(c.getColumnIndex(Notifications.KEY_tags));

                if(tagString != null) {
                    String[] tagArray = tagString.split(",");
                    tag = new HashSet<>(Arrays.asList(tagArray));
                }

                Set<String> cateSet = new HashSet<String>(cate);

                n = new Notifications(name, tag, cateSet, start, end, type, userId);
                if (status == null || status.equals("0")) {
                    n.setActiveStatus(false);
                }
                else {
                    n.setActiveStatus(true);
                }

            } while (c.moveToNext());
        }
        return n;
    }

    public void addNewCategory(String notifId, String category){
        String oldCategoryList = getCategories(notifId);
        String newCategoryList = oldCategoryList + "," + category;

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Notifications.KEY_category, newCategoryList);

        db.update(Notifications.TABLE, values, Post.KEY_id + "= " + notifId, null);
        db.close(); // Closing database connection
    }

    public void deleteCategory(String notifId, String category){
        String oldCategoryList = getCategories(notifId);
        List<String> categoryList = Arrays.asList(oldCategoryList.split(","));
        Set<String> categorySet = new HashSet<String>(categoryList);
        categorySet.remove(category);
        List<String> list = new ArrayList<String>(categorySet);
        String newCategoryList = TextUtils.join(", ", list);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Notifications.KEY_category, newCategoryList);

        db.update(Notifications.TABLE, values, Notifications.KEY_id + "= " + notifId, null);
        db.close(); // Closing database connection
    }

    public String getCategories(String notifId){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Notifications.KEY_category +
                " FROM " + Notifications.TABLE
                + " WHERE " +
                Notifications.KEY_id + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        Log.d("weird", selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(notifId) } );
        String toReturn = "";
        if (cursor.moveToFirst()) {
            do {
                toReturn = cursor.getString(cursor.getColumnIndex(Notifications.KEY_category));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return toReturn;
    }

    public void addNewTag(String notifId, String tag){
        String oldTagList = getTags(notifId);
        String newTagList = oldTagList + "," + tag;

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Notifications.KEY_tags, newTagList);

        db.update(Notifications.TABLE, values, Notifications.KEY_id + "= " + notifId, null);
        db.close(); // Closing database connection
    }

    public void deleteTag(String notifId, String tag){
        String oldTagList = getTags(notifId);
        List<String> tagList = Arrays.asList(oldTagList.split(","));
        Set<String> tagSet = new HashSet<String>(tagList);
        tagSet.remove(tag);
        List<String> list = new ArrayList<String>(tagSet);
        String newTagList = TextUtils.join(", ", list);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Notifications.KEY_tags, newTagList);

        db.update(Notifications.TABLE, values, Notifications.KEY_id + "= " + notifId, null);
        db.close(); // Closing database connection
    }

    public String getTags(String notifId){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Notifications.KEY_tags +
                " FROM " + Notifications.TABLE
                + " WHERE " +
                Notifications.KEY_id + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(notifId) } );
        String toReturn = "";
        if (cursor.moveToFirst()) {
            do {
                toReturn = cursor.getString(cursor.getColumnIndex(Notifications.KEY_tags));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return toReturn;
    }

    public void updateBeginTime(String notifId, String newBeginTime) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Notifications.KEY_beginTime, newBeginTime);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Notifications.TABLE, values, Notifications.KEY_id + "= ?", new String[] { String.valueOf(notifId) });
        db.close(); // Closing database connection
    }

    public void updateEndTime(String notifId, String newEndTime) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Notifications.KEY_endTime, newEndTime);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Notifications.TABLE, values, Notifications.KEY_id + "= ?", new String[] { String.valueOf(notifId) });
        db.close(); // Closing database connection
    }

    public void updateStatus(String notifId, String status) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Notifications.KEY_status, status);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Notifications.TABLE, values, Notifications.KEY_id + "=" + Integer.parseInt(notifId), null);
        db.close(); // Closing database connection
    }

    public void deleteNotification(String notifId){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Notifications.TABLE, Notifications.KEY_id + "= ?", new String[] { String.valueOf(notifId) });
        db.close(); // Closing database connection
    }

    public List<Notifications> getTransNotif(Integer userId) {
        List<Notifications> notifList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // 1st type
        String selectQuery = "SELECT * FROM " + Notifications.TABLE + " where " +
                Notifications.KEY_type + " = 'REQUEST' AND " + Notifications.KEY_userID + " = "
                + userId;

        Cursor c = db.rawQuery(selectQuery, null);
        if(c.moveToFirst()) {
            do {
                Notifications notification = new Notifications();
                notification.setType("REQUEST");
                notification.setRequestID(c.getInt(c.getColumnIndex(Notifications.KEY_requestorID)));
                notification.setActiveStatus(Boolean.parseBoolean(c.getString(c.getColumnIndex(Notifications.KEY_status))));
                notification.setPostID(c.getInt(c.getColumnIndex(Notifications.KEY_postID)));
                notifList.add(notification);
            } while (c.moveToNext());
        }

        // 2nd type
        selectQuery = "SELECT * FROM " + Notifications.TABLE + " where " +
                Notifications.KEY_type + " = 'ACCEPT' AND " + Notifications.KEY_requestorID + " = "
                + userId;

        c = db.rawQuery(selectQuery, null);
        if(c.moveToFirst()) {
            do {
                Notifications notification = new Notifications();
                notification.setType("ACCEPT");
                notification.setRequestID(c.getInt(c.getColumnIndex(Notifications.KEY_requestorID)));
                notification.setPostID(c.getInt(c.getColumnIndex(Notifications.KEY_postID)));
                notification.setProvider(c.getInt(c.getColumnIndex(Notifications.KEY_userID)));
                notifList.add(notification);
            } while (c.moveToNext());
        }

        // 3rd type
        selectQuery = "SELECT * FROM " + Notifications.TABLE + " where " +
                Notifications.KEY_type + " = 'REVIEW' AND " + Notifications.KEY_requestorID + " = "
                + userId;

        c = db.rawQuery(selectQuery, null);
        if(c.moveToFirst()) {
            do {
                Log.d("DEBUG", "we have gotten a review");
                Notifications notification = new Notifications();
                notification.setType("REVIEW");
                notification.setRequestID(c.getInt(c.getColumnIndex(Notifications.KEY_requestorID)));
                notification.setProvider(c.getInt(c.getColumnIndex(Notifications.KEY_userID)));
                notifList.add(notification);
            } while (c.moveToNext());
        }

        db.close();
        return notifList;

    }

    public void insertAccepted(Integer requesterId, Integer postId, Integer userId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Notifications.KEY_requestorID, requesterId);
        values.put(Notifications.KEY_postID, postId);
        values.put(Notifications.KEY_type,"ACCEPT");
        values.put(Notifications.KEY_userID, userId);

        // Inserting Row
        long notification_Id = db.insert(Notifications.TABLE, null, values);
        db.close(); // Closing database connection
    }
}
