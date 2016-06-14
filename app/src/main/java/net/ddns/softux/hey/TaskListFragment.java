package net.ddns.softux.hey;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;

import javax.inject.Inject;


public class TaskListFragment extends BaseFragment implements TaskViewHolder.TaskListActionsListener {

    @Inject
    public DatabaseReference databaseReference;

    private TasksRecylerAdapter recyclerAdapter;
    private RecyclerView taskList;

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
        getHeyComponent().inject(this);

        recyclerAdapter = new TasksRecylerAdapter(databaseReference);
        recyclerAdapter.setTaskListActionsListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        recyclerAdapter.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        recyclerAdapter.stop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_list_fragment, container, false);

        taskList = (RecyclerView) view.findViewById(R.id.task_list);
        taskList.setHasFixedSize(true);
        
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        taskList.setLayoutManager(getLayoutManager());
        taskList.setAdapter(recyclerAdapter);
    }

    public LinearLayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    @Override
    public void onLongClick(Task task) {
        TaskFragment taskFragment = TaskFragment.newInstance(task);
        taskFragment.show(getFragmentManager(), "dialog");
    }
}
