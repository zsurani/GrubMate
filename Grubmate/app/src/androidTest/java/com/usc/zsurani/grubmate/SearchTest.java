package com.usc.zsurani.grubmate;

/**
 * Created by zsurani on 10/27/17.
 */

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.android21buttons.fragmenttestrule.FragmentTestRule;
import com.facebook.FacebookSdk;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SearchTest {
    Context appContext;
    @Before
    public void setup() {

        String description = "description";
        String owner = "1353924581401606";
        String food = "enchiladas";
        String image;

//        Post post = new Post(description, owner, food, image, num_requests, categories, tags,
//                beginTime, endTime, location, active, users, users, homemade_tag);
    }

//    @Rule
//    public ActivityTestRule<MainActivity> mActivityRule =
//            new ActivityTestRule(MainActivity.class);
//
//    @Test
//    public void profileNameMatch() {
//        String name = Profile.getCurrentProfile().getName();
//        onView(withId(R.id.login_button)).perform(cli);
//    }

    @Rule
    public FragmentTestRule<?, MyGroupFragment> mActivityRule =
            FragmentTestRule.create(MyGroupFragment.class);

    @Test
    public void clickButton() throws Exception {

        onView(withId(R.id.create_group)).perform(click());
    }


}
