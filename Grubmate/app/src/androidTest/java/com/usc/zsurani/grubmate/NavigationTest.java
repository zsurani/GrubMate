package com.usc.zsurani.grubmate;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import java.net.URI;

import android.net.Uri;
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

import java.net.URI;
import java.net.URISyntaxException;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.facebook.FacebookSdk.getApplicationContext;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

/**
 * Created by Madison on 10/27/17.
 */

@RunWith(AndroidJUnit4.class)
public class NavigationTest {
    DatabaseHandler dbHandler;
    SQLiteDatabase db;
    Context c;
    UserRepo ur;
    String user_name;
    @Before
    public void setup() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        c = InstrumentationRegistry.getTargetContext();
        dbHandler = new DatabaseHandler(c);
        db = dbHandler.getReadableDatabase();
        dbHandler.delete(db);
        ur = new UserRepo(c);
        mActivityRule.getActivity()
                .getSupportFragmentManager().beginTransaction();

        UserRepo userRepo = new UserRepo(getApplicationContext());
        Profiles p = new Profiles();
        p.setName("Zahra Surani");
        p.setId("1353924581401606");
        String uri =  "https://graph.facebook.com/1353924581401606/picture?height=2147483647&width=2147483647&migration_overrides=%7Boctober_2012%3Atrue%7D";
        p.setUri(Uri.parse(uri));

        userRepo.insertProfile(p);
        User user = new User("Zahra Surani", "1353924581401606");
        userRepo.insert(user);
    }

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void testLogin() {
        Profile p = Profile.getCurrentProfile();
        if(p == null) {
            //MAKE SURE THAT YOU ARE LOGGED OUT COMPLETELY BEFORE TESTING
            //we should test the login button
            //check to make sure that the login in button is saving the correct user to the database
            onView(withId(R.id.login_button)).perform(click());
            p = Profile.getCurrentProfile();
            user_name = p.getName();

            assertEquals(ur.getName(1), p.getName());
        }
    }

    @Test
    public void openMyProfile() {
        onView(withId(R.id.drawer_layout)).perform(actionOpenDrawer());
        onView(withText("My Profile")).perform(click());
    }

    @Test
    public void openMyNewsfeed() {
        onView(withId(R.id.drawer_layout)).perform(actionOpenDrawer());
        onView(withText("My Newsfeed")).perform(click());
    }

    @Test
    public void openMyGroups() {
        onView(withId(R.id.drawer_layout)).perform(actionOpenDrawer());
        onView(withText("My Groups")).perform(click());
    }

    @Test
    public void openMyNotifications() {
        onView(withId(R.id.drawer_layout)).perform(actionOpenDrawer());
        onView(withText("My Notifications")).perform(click());
    }

    @Test
    public void openMyTransactions() {
        onView(withId(R.id.drawer_layout)).perform(actionOpenDrawer());
        onView(withText("My Transactions")).perform(click());
    }

    @Test
    public void openNotificationCenter() {
        onView(withId(R.id.drawer_layout)).perform(actionOpenDrawer());
        onView(withText("Notification Center")).perform(click());
    }

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

}