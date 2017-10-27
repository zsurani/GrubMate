package com.usc.zsurani.grubmate;

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
public class UserTest {
    User u;
    String name;
    String valid_fb_id;
    @Before
    public void setup() {
        name = "name";
        valid_fb_id = "10204210117208549";
    }

    @Test
    public void testConstructor() {
        //when there is a valid facebookID
        u = new User(name, valid_fb_id);
        assertEquals(u.name, name);
        assertEquals(u.facebookUniqueIdentifier, valid_fb_id);

    }

    @Test
    public void testGetFBID() {
        u = new User();

        //check when there isn't
        assertEquals(u.getFBid(), null);


        //checking when there is a FB id
        u.facebookUniqueIdentifier = valid_fb_id;
        assertEquals(u.getFacebookID(), valid_fb_id);
    }

    @Test
    public void testGetRating() {
        u = new User();
        Integer r = 5;

        //check without a rating
        assertEquals(u.getRating(), null);

        //check with rating
        u.rating = r;
        assertEquals(u.getRating(), r);
    }

    @Test
    public void testGetName() {
        u = new User();

        //check without a name
        assertEquals(u.getName(), null);

        //check with name
        u.name = name;
        assertEquals(u.getName(), name);
    }

    @Test
    public void testGetNotification() {
        u = new User();

        //check without any notifications
        assertEquals(u.getNotification(), null);

        //check with notifications
        Set<Integer> not = new HashSet<>();
        not.add(1);
        not.add(2);
        u.notifications = not;
        assertEquals(u.getNotification(), not);
    }


}
