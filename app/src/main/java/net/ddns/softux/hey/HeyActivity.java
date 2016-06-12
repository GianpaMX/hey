package net.ddns.softux.hey;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import javax.inject.Inject;

public class HeyActivity extends BaseActivity implements TaskFragment.NoticeDialogListener<TaskFragment> {

    @Inject
    public TasksApi tasksApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getHeyComponent().inject(this);
        setContentView(R.layout.hey_activity);

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.activity_hey, TaskListFragment.newInstance()).commit();
        }


        FloatingActionButton addButton = (FloatingActionButton) findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskFragment taskFragment = TaskFragment.newInstance();
                taskFragment.show(getSupportFragmentManager(), "dialog");
            }
        });

    }

    @Override
    public void onDialogPositiveClick(TaskFragment dialog) {
        tasksApi.save(dialog.getTask());
    }

    @Override
    public void onDialogNegativeClick(TaskFragment dialog) {
    }
}
