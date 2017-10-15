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

    public void insert(String userId, String ownerId) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Group.KEY_user, userId);
        values.put(Group.KEY_ownerid, ownerId);
        // Inserting Row
        long group_id = db.insert(Group.TABLE, null, values);
        db.close(); // Closing database connection
    }

    public void delete(String groupId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete("Group","id=" + groupId, null);
        db.close(); // Closing database connection
    }

    public void addNewUser(String groupId, String userId){
        String oldUserList = getUsers(groupId);
        String newUserList = oldUserList + "," + userId;

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Group.KEY_user, newUserList);

        db.update(Group.TABLE, values, Group.KEY_id + "= " + groupId, null);
        db.close(); // Closing database connection
    }

    public void deleteUserFromGroup(String groupId, String userId){
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

    public String getUsers(String groupId){
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

    public ArrayList<String>  getGroupsFromOwner(String ownerId) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Group.KEY_id +
                " FROM " + Group.TABLE + " WHERE " + Group.KEY_ownerid + " = " + ownerId;

        //Student student = new Student();
        ArrayList<String> toReturn = new ArrayList<String>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                toReturn.add(cursor.getString(cursor.getColumnIndex(Group.KEY_id)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return toReturn;

    }

}
