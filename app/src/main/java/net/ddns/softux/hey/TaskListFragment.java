package net.ddns.softux.hey;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class TaskListFragment extends Fragment {
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_list_fragment, container, false);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        RecyclerView taskList = (RecyclerView) view.findViewById(R.id.task_list);
        taskList.setLayoutManager(layoutManager);

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
}
