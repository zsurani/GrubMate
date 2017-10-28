package com.usc.zsurani.grubmate;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentActivity;

import com.android21buttons.fragmenttestrule.FragmentTestRule;
import com.facebook.FacebookSdk;
import com.facebook.Profile;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by zsurani on 10/27/17.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EspressoFirst {
    Context appContext;
    @Before
    public void setup() {
        appContext = InstrumentationRegistry.getTargetContext();
        FacebookSdk.sdkInitialize(appContext);
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
