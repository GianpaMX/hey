package net.ddns.softux.hey;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by juan on 5/06/16.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected HeyComponent getHeyComponent() {
        return ((HeyApp)getApplication()).getHeyComponent();
    }
}
