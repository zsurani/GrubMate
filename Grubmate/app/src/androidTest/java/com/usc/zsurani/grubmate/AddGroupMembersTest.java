package com.usc.zsurani.grubmate;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.usc.zsurani.grubmate.activity_and_fragment.AddGroupMembersActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.StringContains.containsString;

/**
 * Created by Madison on 10/27/17.
 */
@RunWith(AndroidJUnit4.class)
public class AddGroupMembersTest {
    @Rule

    public ActivityTestRule<AddGroupMembersActivity> mActivityRule = new ActivityTestRule<AddGroupMembersActivity>(
            AddGroupMembersActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation()
                    .getTargetContext();
            Intent result = new Intent(targetContext, AddGroupMembersActivity.class);
            result.putExtra("groupName", "testGroup");
            return result;
        }
    };

    @Before
    public void setup() {


    }

    @Test
    public void testAddMember() {
        onView(withId(R.id.member_name)).perform(typeText("test"));
        onView(withId(R.id.adding_members)).perform(click());
        onView(withId(R.id.member_name)).check(matches(withText("")));

    }

}
