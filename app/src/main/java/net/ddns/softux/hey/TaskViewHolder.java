package net.ddns.softux.hey;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by juan on 5/06/16.
 */
public class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

    private Task task;

    private final TextView title;
    private final TaskListActionsListener taskListActionsListener;

    public TaskViewHolder(View itemView) {
        this(itemView, null);
    }

    public TaskViewHolder(View itemView, TaskListActionsListener taskListActionsListener) {
        super(itemView);
        this.title = (TextView) itemView.findViewById(R.id.title);
        this.taskListActionsListener = taskListActionsListener;

        itemView.setOnLongClickListener(this);
    }

    public void bind(Task task) {
        this.task = task;
        this.title.setText(task.getTitle());
    }

    @Override
    public boolean onLongClick(View v) {
        if(taskListActionsListener != null) {
            taskListActionsListener.onLongClick(task);
            return true;
        }
        return false;
    }

    public interface TaskListActionsListener {
        void onLongClick(Task task);
    }
}
