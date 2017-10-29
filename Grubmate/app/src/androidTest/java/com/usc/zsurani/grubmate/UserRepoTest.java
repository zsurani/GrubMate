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
    User u;
    UserRepo ur;
    String fbIdentifierTest = "";
    @Before
    public void setup() {
        appContext = InstrumentationRegistry.getTargetContext();
        dbHandler = new DatabaseHandler(appContext);
        db = dbHandler.getReadableDatabase();

        //initalizing the NotificationsRepo
        ur = new UserRepo(appContext);

        //initalizing the Noticition we will use to test
        u = new User();
        u.facebookUniqueIdentifier =  "123";
        u.name = "TestUser";
    }
    @Test
    public void testInsertNotification() throws Exception {
        ur.insert(u);
        Cursor c = db.rawQuery("SELECT * FROM " + User.TABLE
                + " WHERE " + User.KEY_name + " like '%TestUser%'", null);


        // looping through all rows and adding to list

        if (c.moveToFirst()) {
            do {
                fbIdentifierTest = c.getString(c.getColumnIndex(User.KEY_fbUniqueIdentifier));
            } while (c.moveToNext());
        }
        c.close();
        db.close();        assertEquals("123", fbIdentifierTest);
    }
}
