package net.ddns.softux.hey;

import android.app.Application;

/**
 * Created by juan on 5/06/16.
 */

public class HeyApp extends Application {
    private HeyComponent heyComponent;

    public HeyComponent getHeyComponent() {
        if (heyComponent == null) {
            heyComponent = DaggerHeyComponent.builder()
                    .tasksModule(new TasksModule(this))
                    .build();
        }
        return heyComponent;
    }

    public void setHeyComponent(HeyComponent heyComponent) {
        this.heyComponent = heyComponent;
    }
}
