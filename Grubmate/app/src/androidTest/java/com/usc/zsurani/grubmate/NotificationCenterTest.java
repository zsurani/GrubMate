package com.usc.zsurani.grubmate;

import android.content.ClipData;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.core.deps.dagger.Component;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android21buttons.fragmenttestrule.FragmentTestRule;
import com.facebook.FacebookActivity;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.usc.zsurani.grubmate.base_classes.Notifications;
import com.usc.zsurani.grubmate.base_classes.Post;
import com.usc.zsurani.grubmate.base_classes.Profiles;
import com.usc.zsurani.grubmate.base_classes.User;
import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.NotificationsRepo;
import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.PostRepo;
import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.UserRepo;
import com.usc.zsurani.grubmate.databases.DatabaseHandler;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.Is;
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
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
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
public class NotificationCenterTest {
    DatabaseHandler dbHandler;
    SQLiteDatabase db;
    Context c;
    UserRepo ur;

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

        Post post = new Post("description", "1353924581401606", "food", null, "1", "categories", "tags",
                "10:00 pm", "11:00 pm", "home", "true", "", "", "homemade");

        PostRepo postRepo = new PostRepo(getApplicationContext());
        postRepo.insert(post);
    }

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void testRequest() {
        Notifications n = new Notifications(1, 1, 1,
                "location", "REQUEST");
        NotificationsRepo nr = new NotificationsRepo(getApplicationContext());
        nr.insertRequest(n);

        onView(withId(R.id.drawer_layout)).perform(actionOpenDrawer());
        onView(withText("Notification Center")).perform(click());

        final int[] counts = new int[1];
        onView(withId(R.id.list_notification_center_requests)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View view) {
                ListView listView = (ListView) view;

                counts[0] = listView.getCount();

                return true;
            }

            @Override
            public void describeTo(Description description) {

            }
        }));

        assertEquals(counts[0], 1);
    }

    @Test
    public void testAccept() {
        Notifications n = new Notifications(1, 1, 1,
                "location", "REQUEST");
        NotificationsRepo nr = new NotificationsRepo(getApplicationContext());
        nr.insertRequest(n);
        nr.insertAccepted(1, 1, 1);

        onView(withId(R.id.drawer_layout)).perform(actionOpenDrawer());
        onView(withText("Notification Center")).perform(click());

        final int[] counts = new int[1];
        onView(withId(R.id.list_notification_center_requests)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View view) {
                ListView listView = (ListView) view;

                counts[0] = listView.getCount();

                return true;
            }

            @Override
            public void describeTo(Description description) {

            }
        }));

        assertEquals(counts[0], 2);
    }

    @Test
    public void testRating() {
        Notifications n = new Notifications(1, 1, 1,
                "location", "REQUEST");
        Notifications n1 = new Notifications(1, 1,
                1, "REVIEW");
        Notifications n2 = new Notifications(1, 1,
                1, "REVIEW");

        NotificationsRepo nr = new NotificationsRepo(getApplicationContext());
        nr.insertRequest(n);
        nr.insertAccepted(1, 1, 1);
        nr.insertReviewRequest(n1);
        nr.insertReviewRequest(n2);

        onView(withId(R.id.drawer_layout)).perform(actionOpenDrawer());
        onView(withText("Notification Center")).perform(click());

        final int[] counts = new int[1];
        onView(withId(R.id.list_notification_center_requests)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View view) {
                ListView listView = (ListView) view;

                counts[0] = listView.getCount();

                return true;
            }

            @Override
            public void describeTo(Description description) {

            }
        }));

        assertEquals(counts[0], 4);
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
}