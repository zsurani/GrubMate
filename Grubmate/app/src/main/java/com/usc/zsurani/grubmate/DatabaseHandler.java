package com.usc.zsurani.grubmate;

import android.app.Notification;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        //All necessary tables

        String CREATE_TABLE_USER = "CREATE TABLE " + User.TABLE  + "("
                + User.id  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + User.name + " TEXT, "
                + User.facebookUniqueIdentifier + " TEXT, "
                + User.rating + " INTEGER, " +
                + User.numRatings + " INTEGER)";

        db.execSQL(CREATE_TABLE_USER);

        String CREATE_TABLE_POST = "CREATE TABLE " + Post.TABLE  + "("
                + Post.id  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Post.description + " TEXT, "
                + Post.owner + " INTEGER, "
                + Post.food + " TEXT, " +
                + Post.images + " TEXT, "
                + Post.num_requests + " INTEGER, "
                + Post.categories + " TEXT, " +
                + Post.tags + " TEXT, "
                + Post.beginTime + " INTEGER, "
                + Post.endTime + " Integer, " +
                + Post.location + " TEXT, "
                + Post.active + " TEXT, "
                + Post.groups + " TEXT, " +
                + Post.usersRequested + " TEXT, "
                + Post.usersAccepted + " TEXT, "
                + Post.homemadeNotRestaurant + " TEXT, " +
                + Post.allFriendsCanView + " TEXT, " +
                + Post.maxRequesters + " INTEGER)";

        db.execSQL(CREATE_TABLE_POST);

        String CREATE_TABLE_GROUP = "CREATE TABLE " + Group.TABLE  + "("
                + Group.id  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Group.user + " TEXT)";

        db.execSQL(CREATE_TABLE_GROUP);

        String CREATE_TABLE_TRANSACTION = "CREATE TABLE " + TRANSACTION.TABLE  + "("
                + Transaction.id  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Transaction.status + " TEXT, "
                + Transaction.idProvider + " INTEGER, "
                + Transaction.idRequestor + " INTEGER, " +
                + Transaction.locRequestor + " TEXT, " +
                + Transaction.originalPostID + " INTEGER)";

        db.execSQL(CREATE_TABLE_TRANSACTION);

        String CREATE_TABLE_NOTIFICATION = "CREATE TABLE " + NOTIFICATION.TABLE  + "("
                + Notifications.id  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Notifications.userID + " INTEGER, "
                + Notifications.category + " TEXT, "
                + Notifications.tags + " TEXT, " +
                + Notifications.beginTime + " INTEGER, " +
                + Notifications.endTime + " INTEGER, " +
                + Notifications.status + " TEXT, " +
                + Notifications.name + " TEXT, " +
                + Transaction.type + " INTEGER)";

        db.execSQL(CREATE_TABLE_TRANSACTION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + User.TABLE);

        // Create tables again
        onCreate(db);

    }

}