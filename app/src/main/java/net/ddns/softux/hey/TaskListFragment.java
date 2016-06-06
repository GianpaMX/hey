package net.ddns.softux.hey;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import javax.inject.Inject;


public class TaskListFragment extends Fragment {

    @Inject
    public DatabaseReference tasksDatabase;

    private RecyclerView taskList;
    private FirebaseRecyclerAdapter<Task, TaskViewHolder> recyclerAdapter;

    public TaskListFragment() {
        // Required empty public constructor
    }

    public static TaskListFragment newInstance() {
        TaskListFragment fragment = new TaskListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseActivity) getActivity()).getHeyComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_list_fragment, container, false);


        taskList = (RecyclerView) view.findViewById(R.id.task_list);
        taskList.setHasFixedSize(true);

        FloatingActionButton addButton = (FloatingActionButton) view.findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskFragment taskFragment = TaskFragment.newInstance();
                taskFragment.show(getFragmentManager(), "dialog");
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        taskList.setLayoutManager(layoutManager);

        Query query = tasksDatabase.limitToFirst(100);
        recyclerAdapter = new FirebaseRecyclerAdapter<Task, TaskViewHolder>(Task.class, R.layout.task_item_view, TaskViewHolder.class, tasksDatabase) {
            @Override
            protected void populateViewHolder(TaskViewHolder viewHolder, Task task, int position) {
                viewHolder.bind(task);
            }
        };
        taskList.setAdapter(recyclerAdapter);
    }
}
