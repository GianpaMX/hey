package net.ddns.softux.hey.test;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import net.ddns.softux.hey.HeyActivity;
import net.ddns.softux.hey.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static net.ddns.softux.hey.test.Matchers.atPositionOnView;

/**
 * Created by juan on 4/06/16.
 */


@RunWith(AndroidJUnit4.class)
@LargeTest
public class HeyActivityTest {

    @Rule
    public ActivityTestRule<HeyActivity> activityTestRule = new ActivityTestRule<HeyActivity>(HeyActivity.class);

    @Test
    public void testAdTask() {
        String title = "New Task Title";
        String body = "Body of the new task";

        onView(withId(R.id.add)).perform(click());
        onView(withId(R.id.title)).perform(typeText(body), pressImeActionButton());
        onView(withId(R.id.body)).perform(typeText(title), closeSoftKeyboard());
        onView(withId(R.id.save)).perform(click());

        onView(withId(R.id.task_list)).check(matches(atPositionOnView(0, hasDescendant(withText(title)))));
    }
}
