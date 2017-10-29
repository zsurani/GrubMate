package com.usc.zsurani.grubmate;

import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.Checkable;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.Is.isA;

/**
 * Created by Madison on 10/28/17.
 */
@RunWith(AndroidJUnit4.class)
public class CreatePostTest {
    String name = "name";
    String desc = "description";
    String numAvail_StringCorrect = "5";
    String numAvail_StringIncorrect = "test";
    Integer numAvail_Integer = 5;
    String sTime = "1:00PM";
    String eTime = "4:00PM";
    String loc = "loc";
    String tags = "tag1, tag2";
    String tags_incorrect = "tar4 asdlfjasd" +
            "asfalsdjkf";

    @Before
    public void setup() {
        UserRepo userRepo = new UserRepo(InstrumentationRegistry.getTargetContext());
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
    public ActivityTestRule<CreatePostActivity> mActivityRule = new ActivityTestRule<CreatePostActivity>(
            CreatePostActivity.class);

    @Test
    public void testCreatePost() {
        onView(withId(R.id.edit_post_name)).perform(typeText(name));
        onView(withId(R.id.edit_post_desc)).perform(typeText(desc));
        onView(withId(R.id.edit_post_max_requests)).perform(typeText(numAvail_StringCorrect));
        onView(withId(R.id.edit_post_begin_time)).perform(typeText(sTime));
        //onView(withId(R.id.edit_post_end_time)).perform(typeText(eTime));
        //onView(withId(R.id.edit_post_location)).perform(typeText(loc));
        //onView(withId(R.id.edit_post_tags)).perform(typeText(tags));

        //onView(withId(R.id.fastFood)).perform(scrollTo(), setChecked(true));
        //onView(withId(R.id.radio_homemade)).perform(scrollTo(), setChecked(true));

        //still getting the initalization error
        onView(withId(R.id.button_save_new_post)).perform(scrollTo(), click());


    }

    @Test
    public void testIncompletePost() {

    }

    public static ViewAction setChecked(final boolean checked) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return new Matcher<View>() {
                    @Override
                    public boolean matches(Object item) {
                        return isA(Checkable.class).matches(item);
                    }

                    @Override
                    public void describeMismatch(Object item, Description mismatchDescription) {}

                    @Override
                    public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {}

                    @Override
                    public void describeTo(Description description) {}
                };
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public void perform(UiController uiController, View view) {
                Checkable checkableView = (Checkable) view;
                checkableView.setChecked(checked);
            }
        };
    }
}
