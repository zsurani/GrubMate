package com.usc.zsurani.grubmate;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.usc.zsurani.grubmate.base_classes.Group;

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
public class GroupTest {
    Group g;
    Set<String> users;
    String userId;
    String ownerId;
    @Before
    public void setup() {
        users = new HashSet<>();
        users.add("user1");
        users.add("user2");
        users.add("user3");

        userId = "user";

        ownerId = "owner";

    }

    @Test
    public void testConstuctor() {
        g = new Group(users, userId, ownerId);
        assertEquals(g.users, users);
        assertEquals(g.id, userId);
        assertEquals(g.ownerId, ownerId);
    }

    @Test
    public void testSetGetId() {
        g = new Group();
        g.setId(userId);
        assertEquals(g.getId(), userId);

    }

    @Test
    public void testSetGetName() {
        g = new Group();
        g.setName("name");
        assertEquals(g.getName(), "name");
    }


}
