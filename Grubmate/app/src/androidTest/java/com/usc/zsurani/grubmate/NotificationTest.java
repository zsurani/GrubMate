package com.usc.zsurani.grubmate;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

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
public class NotificationTest {
    //local global variables
    Context appContext;
    String name;
    Set<String> tags;
    Set<String> cate;
    String timeStart;
    String timeEnd;
    String type;
    String userId;

    @Before
    public void setup() {
        appContext = InstrumentationRegistry.getTargetContext();

        name = "notification name";

        tags = new HashSet<>();
        tags.add("tag1");
        tags.add("tag2");
        tags.add("tag3");

        cate = new HashSet<>();
        cate.add("cate1");
        cate.add("cate2");
        cate.add("cate3");

        timeStart = "1:00PM";
        timeEnd = "3:00PM";
        type = "type";
        userId = "0";


    }
    @Test
    public void testCreationConstructor1() {
        Notifications n = new Notifications(name, tags, cate, timeStart, timeEnd, type, userId);
        assertEquals(n.name, name);
        assertEquals(n.tags, tags);
        assertEquals(n.category, cate);
        assertEquals(n.beginTime, timeStart);
        assertEquals(n.endTime, timeEnd);
        assertEquals(n.type, type);
        assertEquals(n.userId, userId);
    }
}
