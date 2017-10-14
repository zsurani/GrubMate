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

public class UserRepo {
    private DatabaseHandler dbHelper;

    public UserRepo(Context context) {
        Log.d("DEBUG", "stop");
        dbHelper = new DatabaseHandler(context);
    }

//    public int insert(User user) {
//
//        //Open connection to write data
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(Student.KEY_age, student.age);
//
//        // Inserting Row
//        long student_Id = db.insert(Student.TABLE, null, values);
//        db.close(); // Closing database connection
//        return (int) student_Id;
//    }

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
        Log.d("DEBUG", "here");
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery =  "SELECT  " +
                User.KEY_ID + "," +
                " FROM " + User.TABLE
                + " WHERE " +
                User.KEY_fbUniqueIdentifier + "=?";

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );
        int count = 0;

        if (cursor.moveToFirst()) {
            do {
                User.id =cursor.getInt(cursor.getColumnIndex(User.KEY_ID));
                count++;
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        if (count > 0) {
            return false;
        }
        return true;

    }
}
