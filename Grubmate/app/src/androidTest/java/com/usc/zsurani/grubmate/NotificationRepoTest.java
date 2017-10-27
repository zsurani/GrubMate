package com.usc.zsurani.grubmate;

import android.content.Context;
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
public class NotificationRepoTest {
    //local global variables
    Context appContext;
    DatabaseHandler dbHandler;
    SQLiteDatabase db;
    Notifications n;
    NotificationsRepo nr;
    @Before
    public void setup() {
        appContext = InstrumentationRegistry.getTargetContext();
        dbHandler = new DatabaseHandler(appContext);
        db = dbHandler.getReadableDatabase();

        //initalizing the NotificationsRepo
        nr = new NotificationsRepo(appContext);

        //initalizing the Noticition we will use to test
        n = new Notifications();
        n.userId = 1;
        n.requestID = "1";
        n.location = "location";
        n.postID = 1;
        Set<String> c = new HashSet<>();
        c.add("cate1");
        n.category = c;
        Set<String> t = new HashSet<>();
        t.add("tag1");
        n.tags = t;
        n.beginTime = "1:00PM";
        n.endTime = "2:00PM";
        n.status = true;
        n.name = "name";
        n.type = "type";
    }
    @Test
    public void testInsertNotification() throws Exception {
        //not working right now
        Integer id = nr.insert(n);
        assertEquals(nr.getNotification(id), n);
    }
}
