package com.usc.zsurani.grubmate;

import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.android21buttons.fragmenttestrule.FragmentTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;

/**
 * Created by Madison on 10/28/17.
 */
@RunWith(AndroidJUnit4.class)
public class MyGroupTest {
    @Before
    public void setup() {
        UserRepo userRepo = new UserRepo(InstrumentationRegistry.getTargetContext());
        Profiles p = new Profiles();
        p.setName("Madison Snyder");
        p.setId("10204210117208549");
        String uri =  "https://graph.facebook.com/10204210117208549/picture?height=2147483647&width=2147483647&migration_overrides=%7Boctober_2012%3Atrue%7D";
        p.setUri(Uri.parse(uri));

        userRepo.insertProfile(p);
        User user = new User("Madison Snyder", "10204210117208549");
        userRepo.insert(user);
    }

    @Rule
    public FragmentTestRule<?, MyGroupFragment> mFragmentRule = FragmentTestRule.create(MyGroupFragment.class);

    @Test
    public void testGroup() {
       // onData(allOf(is(instanceOf(Group.class)))).perform(click());

    }
}
