package net.ddns.softux.hey;

import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

/**
 * Created by juan on 5/06/16.
 */
@Singleton
public class TasksApi {

    private Firebase tasksDatabase;

    public TasksApi(Firebase tasksDatabase) {
        this.tasksDatabase = tasksDatabase;
    }

    public void save(Task task) {
        if (task.getKey() == null) {
            task.setKey(tasksDatabase.push().getKey());
        }

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/" + task.getKey(), task.toMap());

        tasksDatabase.updateChildren(childUpdates);
    }
}
