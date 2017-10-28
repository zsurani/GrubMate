package com.usc.zsurani.grubmate;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

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
public class TransactionTest {
    Integer id;
    String status;
    Integer idProvider;
    Integer idRequester;
    Integer postId;
    String loc;
    Transaction t;
    @Before
    public void setup() {
        id = 1;
        status = "open";
        idProvider = 1;
        idRequester = 2;
        postId = 1;
        loc = "location";
    }

    @Test
    public void testConstructor() {
        t = new Transaction(idProvider, idRequester, loc, postId);

        assertEquals(t.idProvider, idProvider);
        assertEquals(t.idRequester, idRequester);
        assertEquals(t.locRequester, loc);
        assertEquals(t.originalPostID, postId);
    }

    @Test
    public void testGetProviderId() {
        t = new Transaction(idProvider, idRequester, loc, postId);
        assertEquals(t.getProviderID(), idProvider);
    }

    @Test
    public void testGetRequesterId() {
        t = new Transaction(idProvider, idRequester, loc, postId);
        assertEquals(t.getRequesterID(), idRequester);
    }

    @Test
    public void testGetPostId() {
        t = new Transaction(idProvider, idRequester, loc, postId);
        assertEquals(t.getPostID(), postId);
    }

    @Test
    public void testGetLocation() {
        t = new Transaction(idProvider, idRequester, loc, postId);
        assertEquals(t.getLocation(), loc);
    }

    @Test
    public void testGetSetStatus() {
        t = new Transaction(idProvider, idRequester, loc, postId);
        //check first with empty
        assertEquals(t.getStatus(), null);

        //check with something
        t.setStatus("active");
        assertEquals(t.getStatus(), "active");
    }

    @Test
    public void testGetSetId() {
        t = new Transaction(idProvider, idRequester, loc, postId);
        //check first with empty
        assertEquals(t.getId(), null);

        //check with something
        Integer i = 1;
        t.setId(i);
        assertEquals(t.getId(), i);
    }
}
