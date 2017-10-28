package com.usc.zsurani.grubmate;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

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

/**
 * Created by Madison on 10/27/17.
 */
@RunWith(AndroidJUnit4.class)
public class EnterLocationTest {

    @Rule
    public ActivityTestRule<EnterLocationActivity> mActivityRule = new ActivityTestRule<EnterLocationActivity>(
            EnterLocationActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation()
                    .getTargetContext();
            Intent result = new Intent(targetContext, AddGroupMembersActivity.class);
            result.putExtra("location", "1");
            result.putExtra("location", "1");
            return result;
        }
    };

    @Before
    public void setup() {

    }

    @Test
    public void testSend() {
        onView(withId(R.id.location_text)).perform(typeText("test location"));
    }
}
