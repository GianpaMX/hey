package net.ddns.softux.hey;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by juan on 5/06/16.
 */

@Singleton
@Component(modules = TasksModule.class)
public interface HeyComponent {
    TasksApi taskApi();
    void inject(HeyActivity activity);
    void inject(TaskListFragment taskListFragment);
}
