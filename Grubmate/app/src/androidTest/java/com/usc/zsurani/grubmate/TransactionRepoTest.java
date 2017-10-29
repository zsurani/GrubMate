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
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by Madison on 10/26/17.
 */
@RunWith(AndroidJUnit4.class)
public class TransactionRepoTest {
    //local global variables
    Context appContext;
    DatabaseHandler dbHandler;
    SQLiteDatabase db;
    Transaction t;
    TransactionRepo tr;
    @Before
    public void setup() {
        appContext = InstrumentationRegistry.getTargetContext();
        dbHandler = new DatabaseHandler(appContext);
        db = dbHandler.getReadableDatabase();

        //initalizing the NotificationsRepo
        tr = new TransactionRepo(appContext);

        //initalizing the Noticition we will use to test
    }
    @Test
    public void testInsertNotification() throws Exception {
        t = new Transaction(3, 2,
                "Parkside", 6);
        tr.insert(t);

        Cursor c = db.rawQuery("SELECT * FROM " + Transaction.TABLE
                + " WHERE " + Transaction.KEY_locRequester + " like '%Parkside%'", null);

        String location = "";
        if (c.moveToFirst()) {
            do {
                location = c.getString(c.getColumnIndex(Transaction.KEY_locRequester));
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        assertEquals(location, "Parkside");
    }
}
