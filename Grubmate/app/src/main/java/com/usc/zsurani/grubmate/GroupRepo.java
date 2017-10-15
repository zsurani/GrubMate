package com.usc.zsurani.grubmate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Shivangi on 10/14/17.
 */

public class GroupRepo {
    private DatabaseHandler dbHelper;

    public GroupRepo(Context context) {
        Log.d("DEBUG", "stop");
        dbHelper = new DatabaseHandler(context);
    }

    public int insert(Integer groupId, Integer userId) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Group.KEY_id, groupId);
        values.put(Group.KEY_user, userId);

        // Inserting Row
        long group_id = db.insert(Group.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) group_id;
    }

    public void delete(Integer groupId, Integer userId) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete("Group","id=" + groupId + " and user=" + userId, null);
        db.close(); // Closing database connection
    }

}
