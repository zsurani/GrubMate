package com.usc.zsurani.grubmate;

import android.app.Notification;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by zsurani on 10/12/17.
 */

public class DatabaseHandler  extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "GrubMate.db";

    public DatabaseHandler(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("SQL", "In databaseHandler constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables

        String CREATE_TABLE_USER = "CREATE TABLE " + User.TABLE  + "("
                + User.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + User.KEY_name + " TEXT, "
                + User.KEY_fbUniqueIdentifier + " TEXT, "
                + User.KEY_rating + " INTEGER, "
                + User.KEY_numRatings + " INTEGER)";

        Log.d("SQL", "based create_table_user");

        db.execSQL(CREATE_TABLE_USER);

        String CREATE_TABLE_POST = "CREATE TABLE " + Post.TABLE  + "("
                + Post.KEY_id  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Post.KEY_description + " TEXT, "
                + Post.KEY_owner + " INTEGER, "
                + Post.KEY_food + " TEXT, "
                + Post.KEY_images + " TEXT, "
                + Post.KEY_num_requests + " INTEGER, "
                + Post.KEY_categories + " TEXT, "
                + Post.KEY_tags + " TEXT, "
                + Post.KEY_beginTime + " INTEGER, "
                + Post.KEY_endTime + " Integer, "
                + Post.KEY_location + " TEXT, "
                + Post.KEY_active + " TEXT, "
                + Post.KEY_groups + " TEXT, "
                + Post.KEY_usersRequested + " TEXT, "
                + Post.KEY_usersAccepted + " TEXT, "
                + Post.KEY_homemadeNotRestaurant + " TEXT, "
                + Post.KEY_allFriendsCanView + " TEXT, "
                + Post.KEY_maxRequesters + " INTEGER)";

        db.execSQL(CREATE_TABLE_POST);

        /*
        String CREATE_TABLE_GROUP = "CREATE TABLE " + Group.TABLE  + "("
                + Group.KEY_id  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Group.KEY_user + " TEXT)";

        db.execSQL(CREATE_TABLE_GROUP);

        String CREATE_TABLE_TRANSACTION = "CREATE TABLE " + Transaction.TABLE  + "("
                + Transaction.KEY_id  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Transaction.KEY_status + " TEXT, "
                + Transaction.KEY_idProvider + " INTEGER, "
                + Transaction.KEY_idRequester + " INTEGER, "
                + Transaction.KEY_locRequester + " TEXT, "
                + Transaction.KEY_originalPostID + " INTEGER)";

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
                + Transaction.KEY_type + " INTEGER)";

        db.execSQL(CREATE_TABLE_TRANSACTION);
        */

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

}