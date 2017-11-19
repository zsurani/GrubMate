package com.usc.zsurani.grubmate;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.usc.zsurani.grubmate.base_classes.Notifications;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Categories;
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
    Integer postId;
    Integer requesterID;
    Integer providerID;
    String loc;
    Boolean accepted;


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

        postId = 0;
        requesterID = 0;
        providerID = 1;
        loc = "location";
        accepted = false;


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
        assertEquals(n.userID, userId);
    }

    @Test
    public void testCreationConstructor2() {
        Notifications n = new Notifications(postId, requesterID, providerID, loc, type);
        assertEquals(n.postID, postId);
        assertEquals(n.requesterID, requesterID);
        assertEquals(n.userId, providerID);
        assertEquals(n.type, type);
        assertEquals(n.location, loc);
    }

    @Test
    public void testCreationConstructor3() {
        Notifications n = new Notifications(postId, requesterID, providerID, accepted, type);
        assertEquals(n.postID, postId);
        assertEquals(n.requestID, Integer.toString(requesterID));
        assertEquals(n.userId, providerID);
        assertEquals(n.status, accepted);
        assertEquals(n.type, type);
    }

    @Test
    public void testCreationConstructor4() {
        Notifications n = new Notifications(postId, requesterID, providerID, type);
        assertEquals(n.postID, postId);
        assertEquals(n.type, type);
        assertEquals(n.userId, providerID);
        assertEquals(n.requesterID, requesterID);
    }

    @Test
    public void testSetGetId() {
        Integer id = 100;
        Notifications n = new Notifications();
        n.setId(id);
        assertEquals(n.getId(), id);
    }

    @Test
    public void testGetCategory() {
        Notifications n = new Notifications();
        n.category = cate;
        assertEquals(n.getCategory(), cate);
    }

    @Test
    public void testGetTags() {
        Notifications n = new Notifications();
        n.tags = tags;
        assertEquals(n.getTags(), tags);
    }

    @Test
    public void testSetGetBeginTime() {
        Notifications n = new Notifications();
        n.setBeginTime(timeStart);
        assertEquals(n.getBeginTime(), timeStart);
    }

    @Test
    public void testSetGetEndTime() {
        Notifications n = new Notifications();
        n.setEndTime(timeEnd);
        assertEquals(n.getEndTime(), timeEnd);
    }

    @Test
    public void testGetUserId() {
        Notifications n = new Notifications();
        n.userID = userId;
        assertEquals(n.getUserId(), userId);
    }

    @Test
    public void testSetGetActiveStatus() {
        Notifications n = new Notifications();
        n.setActiveStatus(true);
        assertEquals(n.isActive(), true);
    }

    @Test
    public void testSetGetName() {
        Notifications n = new Notifications();
        n.name = name;
        assertEquals(n.getName(), name);
    }

    @Test
    public void testSetGetType() {
        Notifications n = new Notifications();
        n.type = type;
        assertEquals(n.getType(), type);
    }

    @Test
    public void testSetGetPostId() {
        Notifications n = new Notifications();
        n.setPostID(postId);
        assertEquals(n.getPostID(), postId);
    }

    @Test
    public void testSetGetRequestId() {
        Notifications n = new Notifications();
        Integer ui = 0;
        n.setRequestID(ui);
        assertEquals(n.getRequestID(), ui);
    }

    @Test
    public void testSetGetProviderId() {
        Notifications n = new Notifications();
        Integer ui = 0;
        n.setProvider(ui);
        assertEquals(n.getProvider(), ui);
    }
}


