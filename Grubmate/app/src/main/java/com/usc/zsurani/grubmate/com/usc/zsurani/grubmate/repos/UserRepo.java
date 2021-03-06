package com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.facebook.Profile;
import com.usc.zsurani.grubmate.databases.DatabaseHandler;
import com.usc.zsurani.grubmate.base_classes.Profiles;
import com.usc.zsurani.grubmate.base_classes.User;

import java.util.ArrayList;

/**
 * Created by zsurani on 10/13/17.
 */

public class UserRepo {
    private DatabaseHandler dbHelper;

    public UserRepo(Context context) {
        Log.d("DEBUG", "stop");
        dbHelper = new DatabaseHandler(context);
    }

    public int insert(User user) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(User.KEY_name, user.getName());
        values.put(User.KEY_fbUniqueIdentifier, user.getFBid());
        values.put(User.KEY_rating, 0);
        values.put(User.KEY_numRatings, 0);

        // Inserting Row
        long user_id = db.insert(User.TABLE, null, values);
        //db.close(); // Closing database connection
        return (int) user_id;
    }

    public int getId(String fbId)
    {
        Log.d("USEID", "in getId");
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT " + User.KEY_ID + " FROM " +
                User.TABLE + " WHERE " + User.KEY_fbUniqueIdentifier + "=" + fbId;
        Log.d("USERID", selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);
        int d = -1;
        if (c.moveToFirst()) {
            do {
                Log.d("USERID", "inside of c.moveToFirst");
                d = c.getInt(c.getColumnIndex(User.KEY_ID));
            } while (c.moveToNext());
        }
        //db.close();
        Log.d("UMBRELLA", Integer.toString(d));
        return d;
    }

    /*
        gets user ID using name
        * used specifically in create group activity
     */
    public int getUserId(String name){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT " + User.KEY_ID + " FROM " + User.TABLE + " WHERE " + User.KEY_name
                + " like '%" + name + "%'";
        Cursor c = db.rawQuery(selectQuery, null);
        int d = -1;
        if(c.moveToFirst()){
            do {
                d = c.getInt(c.getColumnIndex(User.KEY_ID));
            } while (c.moveToNext());
        }
        //db.close();

        return d;
    }

    public String getFBId(int userID){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT " + User.KEY_fbUniqueIdentifier + " FROM " + User.TABLE + " WHERE " + User.KEY_ID + " = " + userID;
        Log.d("TEST", "query: " + selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        String d = "";
        if(c.moveToFirst()){
            do {
                d = c.getString(c.getColumnIndex(User.KEY_fbUniqueIdentifier));
            } while (c.moveToNext());
        }
        //db.close();

        return d;
    }

    /*
        get the User's name from the UserID
     */
    public String getName(int userId){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT " + User.KEY_name + " FROM " + User.TABLE + " WHERE "
                + User.KEY_ID + "=" + userId;
        Cursor c = db.rawQuery(selectQuery, null);
        String d = "";
        if(c.moveToFirst()){
            do {
                d = c.getString(c.getColumnIndex(User.KEY_name));
            } while (c.moveToNext());
        }
        //db.close();

        return d;

    }

//    public void delete(int student_Id) {
//
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        // It's a good practice to use parameter ?, instead of concatenate string
//        db.delete(Student.TABLE, Student.KEY_ID + "= ?", new String[] { String.valueOf(student_Id) });
//        db.close(); // Closing database connection
//    }

//    public void update(Student student) {
//
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//
//        values.put(Student.KEY_age, student.age);
//        values.put(Student.KEY_email,student.email);
//        values.put(Student.KEY_name, student.name);
//
//        // It's a good practice to use parameter ?, instead of concatenate string
//        db.update(Student.TABLE, values, Student.KEY_ID + "= ?", new String[] { String.valueOf(student.student_ID) });
//        db.close(); // Closing database connection
//    }

    public long addReview(String userId, String newReview){
        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(User.KEY_userId, userId);
        values.put(User.KEY_review, newReview);

        // Inserting Row
        long reviewID = db.insert(User.TABLE2, null, values);
        //db.close(); // Closing database connection
        return reviewID;
    }

    public void addRating(String userId, Float nRating){
        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Float oldRating = (float) getRawRating(userId);
        Float newRating = oldRating + nRating;

        Integer oldNum = getRawNumRatings(userId);
        Integer newNum = oldNum + 1;

        ContentValues values = new ContentValues();

        values.put(User.KEY_rating, newRating);
        values.put(User.KEY_numRatings, newNum);

        db.update(User.TABLE, values, User.KEY_ID + "=" + Integer.parseInt(userId), null);

    }

    public Integer getRawRating(String userId){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                User.KEY_rating +
                " FROM " + User.TABLE
                + " WHERE " +
                User.KEY_ID + "= " + userId;// It's a good practice to use parameter ?, instead of concatenate string

        Cursor cursor = db.rawQuery(selectQuery, null);
        Integer toReturn = 0;
        if (cursor.moveToFirst()) {
            do {
                toReturn = cursor.getInt(cursor.getColumnIndex(User.KEY_rating));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return toReturn;
    }

    public Integer getRawNumRatings(String userId){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                User.KEY_numRatings +
                " FROM " + User.TABLE
                + " WHERE " +
                User.KEY_ID + "= " + userId;

        Cursor cursor = db.rawQuery(selectQuery, null);
        Integer toReturn = 0;
        if (cursor.moveToFirst()) {
            do {
                toReturn = cursor.getInt(cursor.getColumnIndex(User.KEY_numRatings));
            } while (cursor.moveToNext());
        }
        cursor.close();
        //db.close();
        return toReturn;
    }

    public String getNumRatings(String userId){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                User.KEY_numRatings +
                " FROM " + User.TABLE
                + " WHERE " +
                User.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(userId) } );
        String toReturn = "";
        if (cursor.moveToFirst()) {
            do {
                toReturn = cursor.getString(cursor.getColumnIndex(User.KEY_numRatings));
            } while (cursor.moveToNext());
        }
        Log.d("DEBUG", "getting rating = " + toReturn);
        cursor.close();
        //db.close();
        return toReturn;
    }

    public Float getRealRating(Integer userId){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT * FROM " + User.TABLE
                + " WHERE " +
                User.KEY_ID + "= " + userId;

        Cursor cursor = db.rawQuery(selectQuery, null);
        int rating = 0;
        int number_of_ratings = 0;
        if (cursor.moveToFirst()) {
            do {
                rating = cursor.getInt(cursor.getColumnIndex(User.KEY_rating));
                number_of_ratings = cursor.getInt(cursor.getColumnIndex(User.KEY_numRatings));
            } while (cursor.moveToNext());
        }
        cursor.close();

        if (number_of_ratings == 0) return (float) 0;
        else {
            return (float) rating / number_of_ratings;
        }
    }

    public String getRating(String userId){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                User.KEY_rating +
                " FROM " + User.TABLE
                + " WHERE " +
                User.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(userId) } );
        String toReturn = "";
        if (cursor.moveToFirst()) {
            do {
                toReturn = cursor.getString(cursor.getColumnIndex(User.KEY_rating));
            } while (cursor.moveToNext());
        }
        cursor.close();
        int num;
        if(userId != "") {
            num = Integer.parseInt(getNumRatings(userId));
        } else {
            num = 0;
        }
        if(num == 0) {
            return Integer.toString(0); //prevents a divide by 0 error
        }

        int rating = Math.round(Float.parseFloat(toReturn)) / num;
        //db.close();
        return Integer.toString(rating);
    }

    public ArrayList<String>  getReviews(String userId) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                User.KEY_review +
                " FROM " + User.TABLE2 + " WHERE " + User.KEY_userId + " = " + userId;

        //Student student = new Student();
        ArrayList<String> toReturn = new ArrayList<String>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                toReturn.add(cursor.getString(cursor.getColumnIndex(User.KEY_review)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        //db.close();
        return toReturn;

    }

    public Boolean newUser(String Id) {
        //Open connection to read only
        Log.d("DEBUG", "here");

        int count = 0;

        Log.d("SQL", "SELECT " + User.KEY_ID + " FROM " + User.TABLE
                + " WHERE " + User.KEY_fbUniqueIdentifier + " = " + Id);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + User.KEY_ID + " FROM " + User.TABLE
                + " WHERE " + User.KEY_fbUniqueIdentifier + " = " + Id, null);
        if(c.moveToFirst()){
            do{
                count++;

            }while(c.moveToNext());
        }
        c.close();
        //db.close();

        if (count > 0) {
            return false;
        }
        return true;
    }

    public Profiles getProfile() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  *  FROM " + User.P_TABLE;

        Profiles p = new Profiles();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                p.setName(cursor.getString(cursor.getColumnIndex(User.KEY_name)));
                p.setId(cursor.getString(cursor.getColumnIndex(User.KEY_ID3)));
                p.setUri(Uri.parse(cursor.getString(cursor.getColumnIndex(User.KEY_image))));
            } while (cursor.moveToNext());
        }

        cursor.close();
        //db.close();
        return p;

    }

    public Profiles getProfile(String fb_id) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery =  "SELECT  *  FROM " + User.P_TABLE + " WHERE " + User.KEY_ID3 + " = " + fb_id;

        Profiles p = new Profiles();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                p.setName(cursor.getString(cursor.getColumnIndex(User.KEY_name)));
                p.setUri(Uri.parse(cursor.getString(cursor.getColumnIndex(User.KEY_image))));
                Log.d("TEST", "name: " + cursor.getString(cursor.getColumnIndex(User.KEY_name)));
                Log.d("TEST", "uri: " + cursor.getString(cursor.getColumnIndex(User.KEY_image)));

            } while (cursor.moveToNext());
        }

        cursor.close();
        //db.close();
        return p;

    }

    public void insertProfile(Profiles user) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(User.KEY_name, user.getName());
        values.put(User.KEY_ID3, user.getId());
        values.put(User.KEY_image, user.getUri().toString());

        // Inserting Row
        long user_id = db.insert(User.P_TABLE, null, values);
        //db.close(); // Closing database connection
    }

    public void insertFriends(String friends) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        int user_id = getId(Profile.getCurrentProfile().getId());
        values.put(User.KEY_userId, user_id);
        values.put(User.KEY_FRIENDS, friends);

        // Inserting Row
        db.insert(User.F_TABLE, null, values);
    }
}
