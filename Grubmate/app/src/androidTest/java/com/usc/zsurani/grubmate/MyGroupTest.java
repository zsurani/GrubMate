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

import com.android21buttons.fragmenttestrule.FragmentTestRule;
import com.facebook.FacebookSdk;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.facebook.FacebookSdk.getApplicationContext;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;

/**
 * Created by Madison on 10/28/17.
 */
@RunWith(AndroidJUnit4.class)
public class MyGroupTest {
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

        UserRepo userRepo = new UserRepo(InstrumentationRegistry.getTargetContext());
        Profiles p = new Profiles();
        p.setName("Madison Snyder");
        p.setId("10204210117208549");
        String uri =  "https://graph.facebook.com/10204210117208549/picture?height=2147483647&width=2147483647&migration_overrides=%7Boctober_2012%3Atrue%7D";
        p.setUri(Uri.parse(uri));

        userRepo.insertProfile(p);
        User user = new User("Madison Snyder", "10204210117208549");
        userRepo.insert(user);

        GroupRepo gr = new GroupRepo(getApplicationContext());
        List<String> m = new ArrayList<String>();
        m.add("1");
        gr.insert("GROUP", m);
    }

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testGroup() {
        onView(withId(R.id.drawer_layout)).perform(actionOpenDrawer());
        onView(withText("My Groups")).perform(click());

        onData(is(instanceOf(MyGroupFragment.GroupAdapter.class)))
                .inAdapterView(withId(R.id.listText));
                //.check(containsString("GROUP"));



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
