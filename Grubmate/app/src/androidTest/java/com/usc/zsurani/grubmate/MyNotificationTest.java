package com.usc.zsurani.grubmate;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ListView;

import com.android21buttons.fragmenttestrule.FragmentTestRule;
import com.facebook.FacebookSdk;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.HashSet;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.core.deps.guava.primitives.Chars.contains;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.internal.util.Checks.checkNotNull;
import static com.facebook.FacebookSdk.getApplicationContext;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by Madison on 10/28/17.
 */
@RunWith(AndroidJUnit4.class)
public class MyNotificationTest {
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

        UserRepo userRepo = new UserRepo(InstrumentationRegistry.getTargetContext());
        Profiles p = new Profiles();
        p.setName("Zahra Surani");
        p.setId("1353924581401606");
        String uri =  "https://graph.facebook.com/1353924581401606/picture?height=2147483647&width=2147483647&migration_overrides=%7Boctober_2012%3Atrue%7D";
        p.setUri(Uri.parse(uri));

        userRepo.insertProfile(p);
        User user = new User("Zahra Surani", "1353924581401606");
        userRepo.insert(user);

        String tags = "tags";
        HashSet<String> t = new HashSet<String>(Arrays.asList(tags));
        HashSet<String> cate = new HashSet<String>();
        cate.add("American");
        String start = "04:20 am";
        String end = "05:20 am";

        Notifications n = new Notifications("TEST", t, cate, start, end, Notifications.TYPE_SUBSCRIPTION, String.valueOf(1));
        n.setActiveStatus(true);

        NotificationsRepo nr = new NotificationsRepo(getApplicationContext());
        nr.insert(n);
    }

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testNotificationsAdapter() {
        onView(withId(R.id.drawer_layout)).perform(actionOpenDrawer());
        onView(withText("My Notifications")).perform(click());

        final int[] counts = new int[1];
        onView(withId(R.id.list_notifications)).check(matches(new TypeSafeMatcher<View>() {
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
    public void verifyButton() {
        onView(withId(R.id.drawer_layout)).perform(actionOpenDrawer());
        onView(withText("My Notifications")).perform(click());
        onView(withText("Add New Notification")).perform(click());
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
