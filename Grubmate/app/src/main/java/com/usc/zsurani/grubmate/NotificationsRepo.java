package com.usc.zsurani.grubmate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
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
}
