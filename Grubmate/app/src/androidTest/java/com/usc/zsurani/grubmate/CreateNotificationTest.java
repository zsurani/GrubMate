package com.usc.zsurani.grubmate;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.Checkable;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
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
public class CreateNotificationTest {
    String name = "name";
    String sTime =  "1:00PM";
    String eTime = "3:00PM";
    String tags = "tag1, tag2";
    @Rule
    public ActivityTestRule<CreateNotificationActivity> mActivityRule = new ActivityTestRule<CreateNotificationActivity>(
            CreateNotificationActivity.class);

    @Test
    public void testCreateNotification() {
        onView(withId(R.id.edit_notification_name)).perform(typeText(name));
        onView(withId(R.id.edit_notification_begin_time)).perform(typeText(sTime));
        onView(withId(R.id.edit_notification_end_time)).perform(typeText(eTime));
        onView(withId(R.id.edit_notification_tags)).perform(typeText(tags));

        //click a checkbox
        onView(withId(R.id.checkBoxAmerican)).perform(scrollTo(), setChecked(true));

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


