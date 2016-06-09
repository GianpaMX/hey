package net.ddns.softux.hey;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by juan on 6/06/16.
 */
public class TasksRecylerAdapter extends RecyclerView.Adapter<TaskViewHolder> implements ChildEventListener {

    private static final String TAG = "TasksRecylerAdapter";

    protected Query query;
    private ArrayList<DataSnapshot> snapshots = new ArrayList<DataSnapshot>();
    private boolean isStarted = false;
    private Comparator<Object> snapshotKeyComparator = new Comparator<Object>() {
        @Override
        public int compare(Object lhs, Object rhs) {
            Log.d(TAG, ((DataSnapshot) lhs).getKey() + " == " + rhs);
            return ((DataSnapshot) lhs).getKey().compareTo((String) rhs);
        }
    };

    public TasksRecylerAdapter() {
    }

    public TasksRecylerAdapter(Query query) {
        this.query = query;
    }

    public void start() {
        if (query != null) {
            query.addChildEventListener(this);
        }
        isStarted = true;
    }

    public void stop() {
        if (query != null) {
            query.removeEventListener(this);
        }
        isStarted = false;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TaskViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public Task getItem(int position) {
        return snapshots.get(position).getValue(Task.class);
    }

    @Override
    public int getItemCount() {
        return snapshots != null ? snapshots.size() : 0;
    }

    private int getIndexForKey(String key) {
        return Collections.binarySearch(snapshots, key, snapshotKeyComparator);
    }

    // Start of ChildEventListener methods
    public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
        if (getIndexForKey(snapshot.getKey()) >= 0)
            return;

        int index = 0;
        if (previousChildKey != null) {
            index = getIndexForKey(previousChildKey) + 1;
        }
        snapshots.add(index, snapshot);
        notifyItemInserted(index);
    }

    public void onChildChanged(DataSnapshot snapshot, String previousChildKey) {
        int index = getIndexForKey(snapshot.getKey());
        snapshots.set(index, snapshot);
        notifyItemChanged(index);
    }

    public void onChildRemoved(DataSnapshot snapshot) {
        int index = getIndexForKey(snapshot.getKey());
        snapshots.remove(index);
        notifyItemRemoved(index);
    }

    public void onChildMoved(DataSnapshot snapshot, String previousChildKey) {
        int oldIndex = getIndexForKey(snapshot.getKey());
        snapshots.remove(oldIndex);
        int newIndex = previousChildKey == null ? 0 : (getIndexForKey(previousChildKey) + 1);
        snapshots.add(newIndex, snapshot);
        notifyItemMoved(oldIndex, newIndex);
    }

    public void onCancelled(DatabaseError firebaseError) {
        // TODO: what do we do with this?
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        boolean restart = isStarted;

        if (isStarted) stop();

        snapshots.clear();
        this.query = query;

        if (restart) start();

        notifyDataSetChanged();
    }
}
