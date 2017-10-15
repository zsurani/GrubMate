package com.usc.zsurani.grubmate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by zsurani on 10/13/17.
 */

public class PostRepo {
    private DatabaseHandler dbHelper;

    public PostRepo(Context context) {
        dbHelper = new DatabaseHandler(context);
    }

    public int insert(Post post) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + User.KEY_ID + " FROM " + User.TABLE
                + " WHERE " + User.KEY_fbUniqueIdentifier + " = " + post.getOwner_string(), null);

        String userId = "";
        if (c.moveToFirst()) {
            userId = c.getString(c.getColumnIndex(User.KEY_ID));
        }

        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Post.KEY_description, post.getDescription());
        values.put(Post.KEY_owner, userId);
        values.put(Post.KEY_food, post.getFood());
        values.put(Post.KEY_num_requests, post.getNum_requests());
        values.put(Post.KEY_categories, post.getCategories());
        values.put(Post.KEY_tags, post.getTag());
        values.put(Post.KEY_beginTime, post.getBeginTime());
        values.put(Post.KEY_endTime, post.getEndTime());
        values.put(Post.KEY_location, post.getLocation());
        values.put(Post.KEY_active, post.getActive_status());
        values.put(Post.KEY_usersAccepted, post.getUserAccepted());
        values.put(Post.KEY_usersRequested, post.getUserRequested());
        values.put(Post.KEY_homemadeNotRestaurant, post.getHomemade());


        // Inserting Row
        long post_id = db.insert(Post.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) post_id;
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

    public Boolean newUser(String Id) {
        //Open connection to read only

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
        db.close();

        if (count > 0) {
            return false;
        }
        return true;
    }

    public Post getPost(int postid) {
        String postId = Integer.toString(postid);
        Post post = new Post();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + Post.TABLE
                + " WHERE " + Post.KEY_id + " = " + postId, null);

        // looping through all rows and adding to list

        if (c.moveToFirst()) {
            do {
                post.setFood(c.getString(c.getColumnIndex(Post.KEY_food)));
                post.setOwner_string(c.getString(c.getColumnIndex(Post.KEY_owner)));
                post.setDescription(c.getString(c.getColumnIndex(Post.KEY_description)));
                post.setHomemade(c.getString(c.getColumnIndex(Post.KEY_homemadeNotRestaurant)));
                post.setNum_requests(c.getString(c.getColumnIndex(Post.KEY_num_requests)));
                post.setBeginTime(c.getString(c.getColumnIndex(Post.KEY_beginTime)));
                post.setEndTime(c.getString(c.getColumnIndex(Post.KEY_endTime)));
                post.setLocation(c.getString(c.getColumnIndex(Post.KEY_location)));
                post.setCategories(c.getString(c.getColumnIndex(Post.KEY_categories)));
                post.setTag(c.getString(c.getColumnIndex(Post.KEY_tags)));
            } while (c.moveToNext());
        }

        c = db.rawQuery("SELECT * FROM " + User.TABLE
                + " WHERE " + User.KEY_ID + " = " + post.getOwner_string(), null);


        if (c.moveToFirst()) {
            post.setOwner_string(c.getString(c.getColumnIndex(User.KEY_name)));
            post.setUser_rating(c.getString(c.getColumnIndex(User.KEY_rating)));
        }

        c.close();
        db.close();

        return post;
    }
}