package com.usc.zsurani.grubmate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.text.TextUtils;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        Log.d("value", groupName);
        Log.d("value", Integer.toString(userId));
        Log.d("value", group_members);

        values.put(Group.KEY_name, groupName);
        values.put(Group.KEY_ownerid, userId);
        values.put(Group.KEY_user, group_members);
        // Inserting Row
        db.insert(Group.TABLE, null, values);
        db.close(); // Closing database connection
    }

    public Group getGroup(String groupId){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  * FROM " + Group.TABLE
                + " WHERE " +
                Group.KEY_id + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(groupId) } );
        Group toReturn = new Group();
        if (cursor.moveToFirst()) {
            do {
                String usersInGroup = cursor.getString(cursor.getColumnIndex(Group.KEY_user));
                List<String> groupList = Arrays.asList(usersInGroup.split(","));
                Set<String> groupSet = new HashSet<String>(groupList);
                toReturn.setUsers(groupSet);
                toReturn.setOwner(cursor.getString(cursor.getColumnIndex(Group.KEY_ownerid)));
                toReturn.setId(cursor.getString(cursor.getColumnIndex(Group.KEY_id)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return toReturn;
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

    public boolean checkIfFriend(String name) {
        final List<String> friendslist = new ArrayList<String>();
        new GraphRequest(AccessToken.getCurrentAccessToken(),"me/friends", null, HttpMethod.GET, new GraphRequest.Callback() {
            public void onCompleted(GraphResponse response) {

                try {
                    JSONObject responseObject = response.getJSONObject();
                    JSONArray dataArray = responseObject.getJSONArray("data");

                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject dataObject = dataArray.getJSONObject(i);
                        String fbName = dataObject.getString("name");
                        friendslist.add(fbName);
                    }
                    List<String> list = friendslist;
                    String friends = "";
                    if (list != null && list.size() > 0) {
                        friends = list.toString();
                        if (friends.contains("[")) {
                            friends = (friends.substring(1, friends.length() - 1));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
//                    hideLoadingProgress();
                }
            }
        }).executeAsync();
        Log.d("CHECK", name);
        for(String str: friendslist) {
            if(str.trim().contains(name.trim())) return true;
        }
        return false;
    }

}
