package net.ddns.softux.hey;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by juan on 5/06/16.
 */
public class TaskViewHolder extends RecyclerView.ViewHolder {

    private final TextView title;

    public TaskViewHolder(View itemView) {
        super(itemView);

        title = (TextView) itemView.findViewById(R.id.title);
    }

    public void bind(Task task) {
        title.setText(task.getTitle());
    }
}
