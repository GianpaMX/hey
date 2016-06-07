package net.ddns.softux.hey.test;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;

import net.ddns.softux.hey.HeyActivity;
import net.ddns.softux.hey.R;
import net.ddns.softux.hey.Task;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.verify;
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
    DatabaseReference databaseReference;

    @Test
    public void testAdTask() {
        when(firebase.push()).thenReturn(firebase);
        when(firebase.getKey()).thenReturn("1");

        Task task = new Task("New Task Title", "Description of the new task");
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/1", task.toMap());

        activityTestRule.launchActivity(null);

        onView(withId(R.id.add)).perform(click());
        onView(withId(R.id.title)).perform(typeText(task.getTitle()), pressImeActionButton());
        onView(withId(R.id.description)).perform(typeText(task.getDescription()), closeSoftKeyboard());
        onView(withText(android.R.string.ok)).perform(click());

        ArgumentCaptor<Map> argumentCaptor = ArgumentCaptor.forClass(Map.class);
        verify(firebase).updateChildren(argumentCaptor.capture());
        Assert.assertEquals(childUpdates, argumentCaptor.getValue());
    }
}
