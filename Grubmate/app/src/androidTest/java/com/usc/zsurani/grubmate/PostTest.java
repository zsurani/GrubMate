package com.usc.zsurani.grubmate;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.usc.zsurani.grubmate.base_classes.Post;
import com.usc.zsurani.grubmate.base_classes.User;

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
public class PostTest {
    Post p;
    Set<String> tags;
    Set<String> cate;
    User u;
    Set<String> group;
    String title;
    String desc;
    String startTime;
    String endTime;
    String loc;
    Boolean visible;
    Integer max;
    Boolean homemade;
    Set<String> image;
    String owner;
    String food;
    Integer id;

    @Before
    public void setup() {
        tags = new HashSet<>();
        tags.add("tag1");
        tags.add("tag2");
        tags.add("tag3");

        cate = new HashSet<>();
        cate.add("cate1");
        cate.add("cate2");
        cate.add("cate3");

        u = new User();

        group = new HashSet<>();
        group.add("group1");
        group.add("group2");
        group.add("group3");

        title = "title";
        desc = "description";
        startTime = "1:00PM";
        endTime = "4:00PM";
        loc = "location";
        visible = true;
        max = 3;
        homemade = true;

        image = new HashSet<>();
        image.add("image1");
        image.add("image2");
        image.add("image3");

        owner = "owner";
        food = "food";
        id = 1;
    }

    @Test
    public void testConstructor() {
        p = new Post(tags, cate, u, group, title, desc, startTime, endTime, loc, image, visible, max, homemade);
        assertEquals(p.tags, tags);
        assertEquals(p.owner, u);
        assertEquals(p.groups, group);
        assertEquals(p.description, desc);
        assertEquals(p.beginTime, startTime);
        assertEquals(p.endTime, endTime);
        assertEquals(p.location, loc);
        assertEquals(p.image, image);
        assertEquals(p.visibleToAll, visible);
        assertEquals(p.maxAccepted, max);
        assertEquals(p.isHomemade, homemade);
    }

    @Test
    public void testSetStatus() {
        p = new Post();
        p.setStatus(true);
        assertEquals(p.active, true);
    }

    @Test
    public void testAddGroup() {
        p = new Post();
        p.addGroup("1");
        Set<String> s = new HashSet<>();
        s.add("1");
        assertEquals(p.getGroups(), s);
    }

    @Test
    public void testSetGetCategory() {
        p = new Post();
        String s = "group1, group2, group3";
        p.setCategories(s);
        assertEquals(p.getCategories(), s);
    }

    @Test
    public void testSetGetTags() {
        p = new Post();
        String t = "tag1, tag2";
        p.setTag(t);
        assertEquals(p.getTag(), t);
    }

    @Test
    public void testSetGetBeginTime() {
        p = new Post();
        p.setBeginTime(startTime);
        assertEquals(p.getBeginTime(), startTime);
    }

    @Test
    public void testSetGetEndTime() {
        p = new Post();
        p.setEndTime(endTime);
        assertEquals(p.getEndTime(), endTime);
    }

    @Test
    public void testSetGetDescription() {
        p = new Post();
        p.setDescription(desc);
        assertEquals(p.getDescription(), desc);
    }

    @Test
    public void testSetGetOwnerString() {
        p = new Post();
        p.setOwner_string(owner);
        assertEquals(p.getOwner_string(), owner);
    }

    @Test
    public void testSetGetFood() {
        p = new Post();
        p.setFood(food);
        assertEquals(p.getFood(), food);
    }

    @Test
    public void testSetGetNumRequest() {
        p = new Post();
        String s = "4";
        p.setNum_requests(s);
        assertEquals(p.getNum_requests(), s);
    }

    @Test
    public void testSetGetLocation() {
        p = new Post();
        p.setLocation(loc);
        assertEquals(p.getLocation(), loc);
    }

    @Test
    public void testSetGetId() {
        p = new Post();
        p.setId(id);
        assertEquals(p.getInt(), id);
    }
}
