package com.usc.zsurani.grubmate;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.usc.zsurani.grubmate.activity_and_fragment.CreateGroupActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import dalvik.annotation.TestTargetClass;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Madison on 10/28/17.
 */
@RunWith(AndroidJUnit4.class)
public class CreateGroupTest {
    @Rule
    public ActivityTestRule<CreateGroupActivity> mActivityRule = new ActivityTestRule<CreateGroupActivity>(
            CreateGroupActivity.class);

    @Test
    public void testAddGroup() {
        onView(withId(R.id.edit_group_name)).perform(typeText("Group Name"));
        onView(withId(R.id.edit_group_name)).check(matches(withText("Group Name")));
        onView(withId(R.id.button_add_members)).perform(click());
        //check that intent is made

    }

    @Test
    public void testBack() {
        onView(withId(R.id.button_back_to_mygroups)).perform(click());
    }

}
