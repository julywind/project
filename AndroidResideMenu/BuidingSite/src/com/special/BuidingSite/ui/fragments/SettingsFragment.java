package com.special.BuidingSite.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.special.BuidingSite.R;
import com.special.BuidingSite.ui.exit.ExitFromSettings;

/**
 * User: special
 * Date: 13-12-22
 * Time: 下午3:28
 * Mail: specialcyci@gmail.com
 */
public class SettingsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.settings, container, false);
        ((Button)root.findViewById(R.id.exit_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickExit(view);
            }
        });
        return root;
    }

    public void onClickExit(View v)
    {
        Context ctx = getActivity();
        if(ctx!=null) {
            Intent intent = new Intent(ctx, ExitFromSettings.class);
            startActivity(intent);
        }
    }
}
