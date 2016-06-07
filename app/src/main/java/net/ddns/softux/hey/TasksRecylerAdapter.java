package net.ddns.softux.hey;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by juan on 6/06/16.
 */
public class TasksRecylerAdapter extends FirebaseRecyclerAdapter<Task, TaskViewHolder> {
    public TasksRecylerAdapter(Query query) {
        super(Task.class, R.layout.task_item_view, TaskViewHolder.class, query);
    }

    public TasksRecylerAdapter(DatabaseReference databseReference) {
        super(Task.class, R.layout.task_item_view, TaskViewHolder.class, databseReference);
    }

        @Override
    protected void populateViewHolder(TaskViewHolder viewHolder, Task task, int position) {
        viewHolder.bind(task);
    }

}
