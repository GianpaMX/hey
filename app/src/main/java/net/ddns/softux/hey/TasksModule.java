package net.ddns.softux.hey;

import android.app.Application;
import android.content.Context;

import com.firebase.client.Firebase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by juan on 5/06/16.
 */

@Module
public class TasksModule {
    protected final Application application;

    public TasksModule(Application application) {
        this.application = application;
    }

    @Provides
    @ApplicationContext
    public Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    public Firebase provideTaskDatabase(@ApplicationContext Context context) {
        Firebase.setAndroidContext(context);
        Firebase firebase = new Firebase(Config.FIREBASE_URL);
        return firebase.child("/tasks");
    }

    @Provides
    @Singleton
    public TasksApi provideTasksApi(Firebase tasksDatabase) {
        return new TasksApi(tasksDatabase);
    }
}
