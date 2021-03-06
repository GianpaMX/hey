package net.ddns.softux.hey.test;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.firebase.client.Firebase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import net.ddns.softux.hey.HeyActivity;
import net.ddns.softux.hey.HeyApp;
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
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by juan on 4/06/16.
 */


@RunWith(AndroidJUnit4.class)
@LargeTest
public class HeyActivityTest {

    @Rule
    public HeyDaggerMockRule mockRule = new HeyDaggerMockRule((HeyApp) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext());

    @Rule
    public ActivityTestRule<HeyActivity> activityTestRule = new ActivityTestRule<HeyActivity>(HeyActivity.class, false, false);

    @Mock
    Firebase firebase;

    @Mock
    DatabaseReference databaseReference;

    @Test
    public void testAddTask() {
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

    @Test
    public void taskModifyTask() {
        when(firebase.push()).thenThrow(new RuntimeException("Push should never been called"));
        activityTestRule.launchActivity(null);

        Task task = new Task("Old Title", null);

        DataSnapshot mockSnapshot = mock(DataSnapshot.class);
        when(mockSnapshot.getValue(any(Class.class))).thenReturn(task);
        when(mockSnapshot.getKey()).thenReturn("1");

        ArgumentCaptor<ChildEventListener> argumentCaptor = ArgumentCaptor.forClass(ChildEventListener.class);
        verify(databaseReference).addChildEventListener(argumentCaptor.capture());

        ChildEventListener adapterEventListener = argumentCaptor.getValue();
        adapterEventListener.onChildAdded(mockSnapshot, null);

        Task modifiedTask = new Task("Modified Task Title", "Modified description of the task");

        onView(withId(R.id.task_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, longClick()));
        onView(withId(R.id.title)).perform(replaceText(modifiedTask.getTitle()), pressImeActionButton());
        onView(withId(R.id.description)).perform(replaceText(modifiedTask.getDescription()), closeSoftKeyboard());
        onView(withText(android.R.string.ok)).perform(click());

        ArgumentCaptor<Map> updateChildrenArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        verify(firebase).updateChildren(updateChildrenArgumentCaptor.capture());

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/1", modifiedTask.toMap());

        Assert.assertEquals(childUpdates, updateChildrenArgumentCaptor.getValue());
    }
}
