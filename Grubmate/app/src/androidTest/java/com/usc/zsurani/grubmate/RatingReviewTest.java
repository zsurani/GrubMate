package com.usc.zsurani.grubmate;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.usc.zsurani.grubmate.activity_and_fragment.AddGroupMembersActivity;
import com.usc.zsurani.grubmate.activity_and_fragment.RatingReviewActivity;

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
public class RatingReviewTest {

    @Before
    public void setup()
    {

    }

    @Rule
    public ActivityTestRule<RatingReviewActivity> mActivityRule = new ActivityTestRule<RatingReviewActivity>(
            RatingReviewActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation()
                    .getTargetContext();
            Intent result = new Intent(targetContext, AddGroupMembersActivity.class);
            result.putExtra("UserRatingId", 1);
            return result;
        }
    };

    @Test
    public void test(){
        onView(withId(R.id.user_rating)).perform(click());
        onView(withId(R.id.edit_review)).perform(typeText("example review"));
        onView(withId(R.id.edit_review)).check(matches(withText("example review")));
        onView(withId(R.id.button_save_rating)).perform(click());
    }
}
