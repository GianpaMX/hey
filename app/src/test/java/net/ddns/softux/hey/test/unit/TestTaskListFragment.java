package net.ddns.softux.hey.test.unit;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.client.Firebase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import junit.framework.Assert;

import net.ddns.softux.hey.BuildConfig;
import net.ddns.softux.hey.HeyApp;
import net.ddns.softux.hey.R;
import net.ddns.softux.hey.TaskListFragment;
import net.ddns.softux.hey.TasksRecylerAdapter;
import net.ddns.softux.hey.test.HeyDaggerMockRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by juan on 7/06/16.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class TestTaskListFragment {
    @Rule
    public HeyDaggerMockRule mockRule = new HeyDaggerMockRule((HeyApp) RuntimeEnvironment.application);

    @Mock
    Firebase firebase;

    @Mock
    DatabaseReference databaseReference;

    @Mock
    LinearLayoutManager mockLayoutManager;

    TestableTaskListFragment fragment;

    @Before
    public void setUp() {
        fragment = new TestableTaskListFragment();
        fragment.setLayoutManager(mockLayoutManager);
        SupportFragmentTestUtil.startFragment(fragment);
    }

    @Test
    public void testDefaultDisplay() {
        RecyclerView.LayoutManager layoutManager = fragment.getRecyclerView().getLayoutManager();
        Assert.assertEquals(mockLayoutManager, layoutManager);
    }

    @Test
    public void testTaskLoad() {
        DataSnapshot mockSnapshot = mock(DataSnapshot.class);

        ArgumentCaptor<ChildEventListener> argumentCaptor = ArgumentCaptor.forClass(ChildEventListener.class);
        verify(databaseReference).addChildEventListener(argumentCaptor.capture());

        ChildEventListener adapterEventListener = argumentCaptor.getValue();
        adapterEventListener.onChildAdded(mockSnapshot, null);

        TasksRecylerAdapter adapter = (TasksRecylerAdapter) fragment.getRecyclerView().getAdapter();
        Assert.assertEquals(1, adapter.getItemCount());
    }

    public static class TestableTaskListFragment extends TaskListFragment {
        private LinearLayoutManager mockLayoutManager;

        @Override
        public LinearLayoutManager getLayoutManager() {
            return mockLayoutManager;
        }

        public void setLayoutManager(LinearLayoutManager mockLayoutManager) {
            this.mockLayoutManager = mockLayoutManager;
        }

        public RecyclerView getRecyclerView() {
            return (RecyclerView) getView().findViewById(R.id.task_list);
        }
    }
}
