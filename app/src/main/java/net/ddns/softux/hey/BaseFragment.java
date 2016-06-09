package net.ddns.softux.hey;

import android.support.v4.app.Fragment;

/**
 * Created by juan on 8/06/16.
 */

public class BaseFragment extends Fragment implements HeyComponentGetter {
    @Override
    public HeyComponent getHeyComponent() {
        return ((HeyApp) getActivity().getApplication()).getHeyComponent();
    }
}
