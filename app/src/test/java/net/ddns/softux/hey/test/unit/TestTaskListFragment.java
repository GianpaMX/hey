package net.ddns.softux.hey.test.unit;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;

import junit.framework.Assert;

import net.ddns.softux.hey.BuildConfig;
import net.ddns.softux.hey.HeyApp;
import net.ddns.softux.hey.R;
import net.ddns.softux.hey.TaskListFragment;
import net.ddns.softux.hey.test.HeyDaggerMockRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

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
        RecyclerView recyclerView = (RecyclerView) fragment.getView().findViewById(R.id.task_list);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        Assert.assertEquals(layoutManager, mockLayoutManager);
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
    }
}
