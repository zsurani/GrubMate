package com.usc.zsurani.grubmate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

        return d;
    }

    /*
        gets user ID using name
        * used specifically in create group activity
     */
    public int getUserId(String name){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT " + User.KEY_ID + " FROM " + User.TABLE + " WHERE " + User.KEY_name
                + "=" + name;
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

        Float oldRating = Float.parseFloat(getRating(userId));
        Float newRating = oldRating + nRating;

        Integer oldNum = Integer.parseInt(getNumRatings(userId));
        Integer newNum = oldNum + 1;
        Log.d("DEBUG", "new Num = " + newNum);

        ContentValues values = new ContentValues();

        values.put(User.KEY_rating, newRating);
        values.put(User.KEY_numRatings, Integer.toString(newNum));

        db.update(User.TABLE, values, User.KEY_ID + "=" + Integer.parseInt(userId), null);

        //db.close();
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

        int num = Integer.parseInt(getNumRatings(userId));
        if(num == 0) {
            return Integer.toString(0); //prevents a divide by 0 error
        }
        int rating = Integer.parseInt(toReturn) / num;
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


}
