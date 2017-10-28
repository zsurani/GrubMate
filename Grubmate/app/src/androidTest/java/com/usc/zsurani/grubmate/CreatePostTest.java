package com.usc.zsurani.grubmate;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Madison on 10/28/17.
 */
@RunWith(AndroidJUnit4.class)
public class CreatePostTest {

    @Rule
    public ActivityTestRule<CreatePostActivity> mActivityRule = new ActivityTestRule<CreatePostActivity>(
            CreatePostActivity.class);

    @Test
    public void testCreatePost() {
        
    }
}
