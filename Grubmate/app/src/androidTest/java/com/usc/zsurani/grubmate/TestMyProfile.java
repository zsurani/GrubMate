package com.usc.zsurani.grubmate;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.core.deps.dagger.Component;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;

import com.android21buttons.fragmenttestrule.FragmentTestRule;
import com.facebook.FacebookActivity;
import com.facebook.FacebookSdk;
import com.facebook.Profile;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.R.attr.fragment;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.facebook.FacebookSdk.getApplicationContext;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import org.junit.runner.RunWith;

/**
 * Created by Madison on 10/27/17.
 */
@RunWith(AndroidJUnit4.class)
public class TestMyProfile {
    DatabaseHandler dbHandler;
    SQLiteDatabase db;
    Context c;
    UserRepo ur;
    String user_name;

    @Before
    public void setup() {
        c = InstrumentationRegistry.getTargetContext();
        dbHandler = new DatabaseHandler(c);
        db = dbHandler.getReadableDatabase();
        //dbHandler.delete(db);
        ur = new UserRepo(c);
    }

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void testLogin() {
        Profile p = Profile.getCurrentProfile();
        if (p == null) {
            //MAKE SURE THAT YOU ARE LOGGED OUT COMPLETELY BEFORE TESTING
            //we should test the login button
            //check to make sure that the login in button is saving the correct user to the database
            onView(withId(R.id.login_button)).perform(click());
            p = Profile.getCurrentProfile();
            user_name = p.getName();

            assertEquals(ur.getName(1), p.getName());
        }
    }


/*

    @Rule
    public FragmentTestRule<?, ProfileFragment> mFragmentRule = FragmentTestRule.create(ProfileFragment.class);


    @Test
    public void testMyProfile() {
        //navigate to the profile page
        //onView(withId(R.id.drawer_layout)).perform(actionOpenDrawer());
        //onView(withText("My Profile")).perform(click());
        //SystemClock.sleep(1000);
        //make sure that the picture, name, rating and review work
        //mActivityRule.getActivity().getSupportFragmentManager().beginTransaction();
        //onView(withId(R.id.label_name)).check(matches(withText(Profile.getCurrentProfile().getName())));
        //FragmentTestUtil.startVisibleFragment(fragment);
    }

    @Test
    public void pleaseWork() {
        onView(withText("Create Post")).perform(click());
       // onView(withText(R.string.button_clicked)).check(matches(isDisplayed()));
    }


/*
    private static ViewAction actionOpenDrawer() {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(DrawerLayout.class);
            }

            @Override
            public String getDescription() {
                return "open drawer";
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((DrawerLayout) view).openDrawer(GravityCompat.START);
            }
        };
    }
    private static ViewAction actionCloseDrawer() {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(DrawerLayout.class);
            }

            @Override
            public String getDescription() {
                return "close drawer";
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((DrawerLayout) view).closeDrawer(GravityCompat.START);
            }
        };
    }
*/
}


