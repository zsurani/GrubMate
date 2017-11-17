package com.usc.zsurani.grubmate;

import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.Checkable;

import com.usc.zsurani.grubmate.activity_and_fragment.CreateNotificationActivity;
import com.usc.zsurani.grubmate.base_classes.Profiles;
import com.usc.zsurani.grubmate.base_classes.User;
import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.UserRepo;

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
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.facebook.FacebookSdk.getApplicationContext;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.Is.isA;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by Madison on 10/28/17.
 */
@RunWith(AndroidJUnit4.class)
public class CreateNotificationTest {
    String name = "name";
    String sTime =  "1:00PM";
    String eTime = "3:00PM";
    String tags = "tag1, tag2";
    @Rule
    public ActivityTestRule<CreateNotificationActivity> mActivityRule = new ActivityTestRule<CreateNotificationActivity>(
            CreateNotificationActivity.class);

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

    @Test
    public void testCreateNotification() {
        onView(withId(R.id.edit_notification_name)).perform(typeText(name));
        onView(withId(R.id.edit_notification_begin_time)).perform(typeText(sTime));
        onView(withId(R.id.edit_notification_end_time)).perform(typeText(eTime));
        onView(withId(R.id.edit_notification_tags)).perform(typeText(tags));

        //click a checkbox
        onView(withId(R.id.checkBoxAmerican)).perform(scrollTo(), setChecked(true));

    }

    @Test
    public void testCreateNotificationIncorrectly() {
        //This is still getting the SDK error
        onView(withId(R.id.button_save_new_notification)).perform(scrollTo(), click());
        /*
        CreateNotificationActivity activity = mActivityRule.getActivity();
        onView(withText("Please fill out all fields and try again")).
                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
                check(matches(isDisplayed()));
                */
        onView(withText("Please fill out all fields and try again")).inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
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


