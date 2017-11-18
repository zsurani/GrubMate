package com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.text.TextUtils;

import com.facebook.Profile;
import com.usc.zsurani.grubmate.databases.DatabaseHandler;
import com.usc.zsurani.grubmate.base_classes.Group;
import com.usc.zsurani.grubmate.base_classes.User;

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
    Context context;

    public GroupRepo(Context context) {
        Log.d("DEBUG", "stop");
        dbHelper = new DatabaseHandler(context);
        this.context = context;
    }

    public void insert(String groupName, List<String> members) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String group_members = "";
        Log.d("members", members.toString());

        for (int i=0; i<members.size(); i++) {
            Cursor c = db.rawQuery("SELECT * FROM " + User.TABLE
                    + " WHERE " + User.KEY_name + " like '" + members.get(i) + "'", null);
            int d = 0;
            if (c.moveToFirst()) {
                d = c.getInt(c.getColumnIndex(User.KEY_ID));
            }
            group_members += Integer.toString(d);
            if (i != members.size()-1) group_members += ", ";
        }

        String selectQuery = "SELECT " + User.KEY_ID + " FROM " +
                User.TABLE + " WHERE " + User.KEY_fbUniqueIdentifier + "=" + Profile.getCurrentProfile().getId();

        Cursor c = db.rawQuery(selectQuery, null);
        int userId = -1;
        if (c.moveToFirst()) userId = c.getInt(c.getColumnIndex(User.KEY_ID));

        selectQuery =  "SELECT * FROM " + Group.TABLE + " WHERE " +
                Group.KEY_name + " LIKE '" + groupName + "'";
        Boolean found = false;

        String key_id = "";
        c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                found = true;
                key_id = c.getString(c.getColumnIndex(Group.KEY_id));
            } while (c.moveToNext());
        }

        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        Log.d("value", groupName);
        Log.d("value", Integer.toString(userId));
        Log.d("value", group_members);

        values.put(Group.KEY_name, groupName);
        values.put(Group.KEY_ownerid, userId);
        values.put(Group.KEY_user, group_members);
        // Inserting Row
        if (!found) db.insert(Group.TABLE, null, values);
        else db.update(Group.TABLE, values, Group.KEY_id+"="+key_id, null);
        db.close(); // Closing database connection
        c.close();
    }

    public Group getGroup(String groupId){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  * FROM " + Group.TABLE
                + " WHERE " +
                Group.KEY_id + "=" + groupId;// It's a good practice to use parameter ?, instead of concatenate string

        Cursor cursor = db.rawQuery(selectQuery, null);
        Group toReturn = new Group();
        if (cursor.moveToFirst()) {
            do {
                String usersInGroup = cursor.getString(cursor.getColumnIndex(Group.KEY_user));
                List<String> groupList = Arrays.asList(usersInGroup.split(","));
                Set<String> groupSet = new HashSet<String>(groupList);
                toReturn.setUsers(groupSet);
                toReturn.setOwner(cursor.getString(cursor.getColumnIndex(Group.KEY_ownerid)));
                toReturn.setId(cursor.getString(cursor.getColumnIndex(Group.KEY_id)));
                toReturn.setName(cursor.getString(cursor.getColumnIndex(Group.KEY_name)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return toReturn;
    }

    public String getGroupID(String name){

        List<String> groupNames = Arrays.asList(name.split(","));
        List<String> groupId = new ArrayList<>();

        for (int i=0; i< groupNames.size(); i++) {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String selectQuery = "SELECT * FROM " + Group.TABLE + " WHERE " + Group.KEY_name + " like '" +
                    groupNames.get(i).trim() + "'";

            Cursor c = db.rawQuery(selectQuery, null);
            int d = -1;
            if (c.moveToFirst()) {
                do {
                    groupId.add(Integer.toString(c.getInt(c.getColumnIndex(User.KEY_ID))));
                } while (c.moveToNext());
            }
        }

        StringBuilder csvBuilder = new StringBuilder();
        String SEPARATOR = ",";

        for(String id : groupId){
            csvBuilder.append(id);
            csvBuilder.append(SEPARATOR);
        }

        String csv = csvBuilder.toString();
        csv = csv.substring(0, csv.length() - SEPARATOR.length());

        return csv;
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

    public boolean checkIfFriend(Integer user_id, String name) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        UserRepo userRepo = new UserRepo(context);
        String selectQuery =  "SELECT  " +
                User.KEY_FRIENDS +
                " FROM " + User.F_TABLE
                + " WHERE " +
                User.KEY_userId + " = " + user_id;

        Cursor cursor = db.rawQuery(selectQuery, null);
        String friends = "";
        if (cursor.moveToFirst()) {
            do {
                friends = cursor.getString(cursor.getColumnIndex(User.KEY_FRIENDS));
            } while (cursor.moveToNext());
        }

        List<String> userFriends = Arrays.asList(friends.split(", "));
        if (userFriends.contains(name)) return true;
        return false;
    }

    public boolean checkIfUser(String name) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        UserRepo userRepo = new UserRepo(context);
        String selectQuery =  "SELECT *" +
                " FROM " + User.TABLE
                + " WHERE " +
                User.KEY_name + " LIKE '" + name + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);
        int count = 0;
        if (cursor.moveToFirst()) {
            do {
                count++;
            } while (cursor.moveToNext());
        }

        if (count > 0) return true;
        return false;
    }

    public List<String> getUser(String groupName){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Group.KEY_user +
                " FROM " + Group.TABLE
                + " WHERE " +
                Group.KEY_name + " LIKE '" + groupName + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);
        String toReturn = "";
        List<String> usersIdList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                toReturn = cursor.getString(cursor.getColumnIndex(Group.KEY_user));
            } while (cursor.moveToNext());
        }

        List<String> usersList = new ArrayList<>();
        if (toReturn.equals("")) return usersList;

        usersList = Arrays.asList(toReturn.split(","));
        Log.d("debugging", usersList.toString());
        for(int i = 0; i < usersList.size(); i++) {
            Cursor c = db.rawQuery("SELECT * FROM " + User.TABLE + " WHERE " + User.KEY_ID + "=" + usersList.get(i), null);
            Log.d("debugging", "SELECT * FROM " + User.TABLE + " WHERE " + User.KEY_ID + "=" + usersList.get(i));
            String d = "";
            if (c.moveToFirst()) {
                d = c.getString(c.getColumnIndex(User.KEY_name));
            }
            Log.d("weird", d);
            usersIdList.add(d);
        }
        Log.d("debugging", usersIdList.toString());

        cursor.close();
        db.close();

        return usersIdList;
    }

}