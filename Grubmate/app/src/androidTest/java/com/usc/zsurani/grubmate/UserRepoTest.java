package com.usc.zsurani.grubmate;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by Madison on 10/26/17.
 */
@RunWith(AndroidJUnit4.class)
public class UserRepoTest {
    //local global variables
    Context appContext;
    DatabaseHandler dbHandler;
    SQLiteDatabase db;
    UserRepo ur;
    String fbIdentifierTest = "";
    @Before
    public void setup() {
        appContext = InstrumentationRegistry.getTargetContext();
        dbHandler = new DatabaseHandler(appContext);
        db = dbHandler.getReadableDatabase();

        //initalizing the NotificationsRepo
        ur = new UserRepo(appContext);
        dbHandler.delete(db);

        //initalizing the Noticition we will use to test
    }
    @Test
    public void testInsertUser() throws Exception {
        User u = new User();
        u.facebookUniqueIdentifier =  "12345";
        u.name = "TestUser3";
        ur.insert(u);
        Cursor c = db.rawQuery("SELECT * FROM " + User.TABLE
                + " WHERE " + User.KEY_name + " like '%TestUser3%'", null);


        // looping through all rows and adding to list

        if (c.moveToFirst()) {
            do {
                fbIdentifierTest = c.getString(c.getColumnIndex(User.KEY_fbUniqueIdentifier));
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        assertEquals("12345", fbIdentifierTest);
    }

    @Test
    public void testGetID() throws Exception {
        User u = new User();
        u.facebookUniqueIdentifier =  "12345";
        u.name = "TestUser3";
        ur.insert(u);
        assertEquals(1, ur.getId("12345"));
    }

    @Test
    public void testAddRating() throws Exception {
        User u = new User();
        u.facebookUniqueIdentifier =  "12345";
        u.name = "TestUser3";
        ur.insert(u);
        float rating = 8;
        ur.addRating("1", rating);
        assertEquals("8", ur.getRating("1"));
    }

    @Test
    public void testNewUser() throws Exception {
        UserRepo ur = new UserRepo(appContext);
        User u = new User();
        u.facebookUniqueIdentifier =  "12345";
        u.name = "TestUser3";
        ur.insert(u);

        assertEquals("true", "" + ur.newUser("56"));
        assertEquals("false", "" + ur.newUser("12345"));
    }
}
