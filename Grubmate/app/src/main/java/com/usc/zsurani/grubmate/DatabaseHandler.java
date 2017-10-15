package com.usc.zsurani.grubmate;

import android.app.Notification;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by zsurani on 10/12/17.
 */

public class DatabaseHandler  extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "GrubMate.db";

    public DatabaseHandler(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_USER = "CREATE TABLE " + User.TABLE  + "("
                + User.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + User.KEY_name + " TEXT, "
                + User.KEY_fbUniqueIdentifier + " TEXT, "
                + User.KEY_rating + " INTEGER, "
                + User.KEY_numRatings + " INTEGER)";

        Log.d("SQL", CREATE_TABLE_USER);

        db.execSQL(CREATE_TABLE_USER);

        String CREATE_TABLE_USERTOREVIEW = "CREATE TABLE " + User.TABLE2  + "("
                + User.KEY_ID2  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + User.KEY_userId + " INTEGER, "
                + User.KEY_review + " TEXT)";

        Log.d("SQL", CREATE_TABLE_USERTOREVIEW);

        db.execSQL(CREATE_TABLE_USERTOREVIEW);

        String CREATE_TABLE_POST = "CREATE TABLE " + Post.TABLE  + "("
                + Post.KEY_id  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Post.KEY_description + " TEXT, "
                + Post.KEY_owner + " INTEGER, "
                + Post.KEY_food + " TEXT, "
                + Post.KEY_images + " BLOB, "
                + Post.KEY_num_requests + " TEXT, "
                + Post.KEY_categories + " TEXT, "
                + Post.KEY_tags + " TEXT, "
                + Post.KEY_beginTime + " TEXT, "
                + Post.KEY_endTime + " TEXT, "
                + Post.KEY_location + " TEXT, "
                + Post.KEY_active + " TEXT, "
                + Post.KEY_groups + " TEXT, "
                + Post.KEY_usersRequested + " TEXT, "
                + Post.KEY_usersAccepted + " TEXT, "
                + Post.KEY_homemadeNotRestaurant + " TEXT, "
                + Post.KEY_allFriendsCanView + " TEXT)";

        Log.d("SQL", CREATE_TABLE_POST);

        db.execSQL(CREATE_TABLE_POST);

        String CREATE_TABLE_GROUP = "CREATE TABLE " + Group.TABLE  + "("
                + Group.KEY_id  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Group.KEY_ownerid + "TEXT, "
                + Group.KEY_user + " TEXT)";

        Log.d("SQL", CREATE_TABLE_GROUP);

        db.execSQL(CREATE_TABLE_GROUP);

        String CREATE_TABLE_TRANSACTION = "CREATE TABLE " + Transaction.TABLE  + "("
                + Transaction.KEY_id  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Transaction.KEY_status + " TEXT, "
                + Transaction.KEY_idProvider + " INTEGER, "
                + Transaction.KEY_idRequester + " INTEGER, "
                + Transaction.KEY_locRequester + " TEXT, "
                + Transaction.KEY_originalPostID + " INTEGER)";

        Log.d("SQL", CREATE_TABLE_TRANSACTION);

        db.execSQL(CREATE_TABLE_TRANSACTION);

        String CREATE_TABLE_NOTIFICATION = "CREATE TABLE " + Notifications.TABLE  + "("
                + Notifications.KEY_id  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Notifications.KEY_userID + " INTEGER, "
                + Notifications.KEY_category + " TEXT, "
                + Notifications.KEY_tags + " TEXT, "
                + Notifications.KEY_beginTime + " INTEGER, "
                + Notifications.KEY_endTime + " INTEGER, "
                + Notifications.KEY_status + " TEXT, "
                + Notifications.KEY_name + " TEXT, "
                + Notifications.KEY_type + " TEXT)";

        Log.d("SQL", CREATE_TABLE_NOTIFICATION);

        db.execSQL(CREATE_TABLE_NOTIFICATION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + User.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Post.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Group.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Transaction.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Notifications.TABLE);

        // Create tables again
        onCreate(db);

    }

    public void delete(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + User.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Post.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Group.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Transaction.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Notifications.TABLE);
        onCreate(db);
    }

    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "message" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }
    }

}