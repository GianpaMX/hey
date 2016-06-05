package net.ddns.softux.hey.test;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.firebase.client.Firebase;

import net.ddns.softux.hey.HeyActivity;
import net.ddns.softux.hey.R;
import net.ddns.softux.hey.TasksApi;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by juan on 4/06/16.
 */


@RunWith(AndroidJUnit4.class)
@LargeTest
public class HeyActivityTest {

    @Rule
    public HeyDaggerMockRule mockRule = new HeyDaggerMockRule();

    @Rule
    public ActivityTestRule<HeyActivity> activityTestRule = new ActivityTestRule<HeyActivity>(HeyActivity.class, false, false);

    @Mock
    Firebase firebase;

    @Mock
    TasksApi tasksApi;

    @Test
    public void testAdTask() {
        when(firebase.push()).thenReturn(mock(Firebase.class));
        when(firebase.getKey()).thenReturn("1");

        activityTestRule.launchActivity(null);

        String title = "New Task Title";
        String description = "Description of the new task";

        onView(withId(R.id.add)).perform(click());
        onView(withId(R.id.title)).perform(typeText(title), pressImeActionButton());
        onView(withId(R.id.description)).perform(typeText(description), closeSoftKeyboard());
        onView(withText(android.R.string.ok)).perform(click());

        onView(withId(R.id.task_list)).check(matches(atPositionOnView(0, hasDescendant(withText(title)))));
    }
}
