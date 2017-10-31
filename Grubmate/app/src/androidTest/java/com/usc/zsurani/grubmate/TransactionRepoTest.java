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
    TransactionRepo tr;
    @Before
    public void setup() {
        appContext = InstrumentationRegistry.getTargetContext();
        dbHandler = new DatabaseHandler(appContext);
        db = dbHandler.getReadableDatabase();

        tr = new TransactionRepo(appContext);
        dbHandler.delete(db);
    }

    @Test
    public void testInsertNotification() throws Exception {
        Transaction t = new Transaction(3, 2,
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

    @Test
    public void testGetTransactionIDs() throws Exception {
        Transaction t = new Transaction(3, 4,
                "Parkside", 6);
        tr.insert(t);
        Transaction t2 = new Transaction(3, 5,
                "Parkside", 6);
        tr.insert(t2);
        Transaction t3 = new Transaction(3, 9,
                "Parkside", 6);
        tr.insert(t3);

        List<String> result = tr.getTransactionsId(3);
        assertEquals(3, result.size());
    }

    @Test
    public void testGetTransaction() throws Exception {
        Transaction t = new Transaction(3, 4,
                "Parkside", 6);
        Transaction t2 = new Transaction(3, 4,
                "Webb", 6);
        tr.insert(t);
        tr.insert(t2);

        Transaction result = tr.getTransaction(1);
        assertEquals(result.locRequester, "Parkside");
    }

    @Test
    public void testUpdateStatus() throws Exception {
        Transaction t = new Transaction(3, 4,
                "Parkside", 6);
        tr.insert(t);
        tr.updateStatus(1, "2");

        Cursor c = db.rawQuery("SELECT * FROM " + Transaction.TABLE
                + " WHERE " + Transaction.KEY_id + " like '1'", null);

        String status = "";
        if (c.moveToFirst()) {
            do {
                status = c.getString(c.getColumnIndex(Transaction.KEY_status));
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        assertEquals(status, "2");
    }
}
