package com.usc.zsurani.grubmate;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.usc.zsurani.grubmate.activity_and_fragment.AddGroupToPostActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by Madison on 10/27/17.
 */

@RunWith(AndroidJUnit4.class)
public class AddGroupToPostTest {
    @Rule
    public ActivityTestRule<AddGroupToPostActivity> mActivityRule = new ActivityTestRule<AddGroupToPostActivity>(AddGroupToPostActivity.class);


    @Before
    public void setup() {

    }

    @Test
    public void testSaveGroup() {
        onView(withId(R.id.adding_group)).perform(typeText("test group"));
        onView(withId(R.id.saving_post)).perform(click());
        //could add a part that checks for the intent that is sent, but the dependency isn't importing
    }

}
