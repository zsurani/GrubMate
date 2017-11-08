package com.usc.zsurani.grubmate;

/**
 * Created by zsurani on 10/27/17.
 */

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.ListView;

import com.android21buttons.fragmenttestrule.FragmentTestRule;
import com.facebook.FacebookSdk;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
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
import static com.facebook.FacebookSdk.getApplicationContext;
import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class SearchTest {

    DatabaseHandler dbHandler;
    SQLiteDatabase db;
    Context c;
    UserRepo ur;

    @Before
    public void setup() {
        c = InstrumentationRegistry.getTargetContext();
        dbHandler = new DatabaseHandler(c);
        db = dbHandler.getReadableDatabase();
        dbHandler.delete(db);
        ur = new UserRepo(c);

        UserRepo userRepo = new UserRepo(c);
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

        PostRepo postRepo = new PostRepo(c);
        postRepo.insert(post);
    }

    @Rule
    public ActivityTestRule<SearchResultsActivity> mActivityRule = new ActivityTestRule<SearchResultsActivity>(
            SearchResultsActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation()
                    .getTargetContext();
            // TODO eliminate this test?
            Intent result = new Intent(targetContext, SearchResultsActivity.class);
            result.putExtra("SearchParam", "food");
            return result;
        }
    };

    public void testSearch() {
        final int[] counts = new int[1];
        onView(withId(R.layout.activity_search_results)).check(matches(new TypeSafeMatcher<View>() {
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
}
