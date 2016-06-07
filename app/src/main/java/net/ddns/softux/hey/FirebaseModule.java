package net.ddns.softux.hey;

import android.content.Context;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by juan on 5/06/16.
 */

@Module
public class FirebaseModule {
    @Provides
    @Singleton
    public Firebase provideTasksFirebase(@ApplicationContext Context context) {
        Firebase.setAndroidContext(context);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
        Firebase firebase = new Firebase(Config.FIREBASE_URL);
        return firebase.child("/tasks");
    }

    @Provides
    @Singleton
    public DatabaseReference provideTasksDabaseReference(Firebase taskFirebase) {
        return FirebaseDatabase.getInstance().getReference().child("tasks");
    }
}
