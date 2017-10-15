package com.usc.zsurani.grubmate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.text.TextUtils;
import java.util.List;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;

/**
 * Created by Shivangi on 10/14/17.
 */

public class GroupRepo {
    private DatabaseHandler dbHelper;

    public GroupRepo(Context context) {
        Log.d("DEBUG", "stop");
        dbHelper = new DatabaseHandler(context);
    }

    //Make return groupId
    public String insert(String userId) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Group.KEY_user, userId);

        // Inserting Row
        long group_id = db.insert(Group.TABLE, null, values);
        db.close(); // Closing database connection
        return (String) userId;
    }

    public void delete(Integer groupId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete("Group","id=" + groupId, null);
        db.close(); // Closing database connection
    }

    public void addNewUser(Integer groupId, Integer userId){
        String oldUserList = getUsers(groupId);
        String newUserList = oldUserList + "," + userId;

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Group.KEY_user, newUserList);

        db.update(Group.TABLE, values, Group.KEY_id + "= " + groupId, null);
        db.close(); // Closing database connection
    }

    public void deleteUserFromGroup(Integer groupId, Integer userId){
        String oldUserList = getUsers(groupId);
        List<String> usersList = Arrays.asList(oldUserList.split(","));
        Set<String> usersSet = new HashSet<String>(usersList);
        usersSet.remove(userId);
        List<String> list = new ArrayList<String>(usersSet);
        String newUserList = TextUtils.join(", ", list);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Group.KEY_user, newUserList);

        db.update(Group.TABLE, values, Group.KEY_id + "= " + groupId, null);
        db.close(); // Closing database connection
    }

    public String getUsers(Integer groupId){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Group.KEY_user +
                " FROM " + Group.TABLE
                + " WHERE " +
                Group.KEY_id + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(groupId) } );
        String toReturn = "";
        if (cursor.moveToFirst()) {
            do {
                toReturn = cursor.getString(cursor.getColumnIndex(Group.KEY_user));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return toReturn;
    }

}
