package com.usc.zsurani.grubmate;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Created by Madison on 10/26/17.
 */
@RunWith(AndroidJUnit4.class)
public class NotificationRepoTest {
    //local global variables
    Context appContext;
    DatabaseHandler dbHandler;
    SQLiteDatabase db;

    @Before
    public void setup() {
        appContext = InstrumentationRegistry.getTargetContext();
        dbHandler = new DatabaseHandler(appContext);
        db = dbHandler.getReadableDatabase();
    }
    @Test
    public void insertNotification() throws Exception {
        Notifications n = new Notifications();
        n.setActiveStatus(true);
        assertEquals(n.status, true);
    }
}
