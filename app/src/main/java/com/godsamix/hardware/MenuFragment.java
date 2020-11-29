package com.godsamix.hardware;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.godsamix.hardware.R;

public class MenuFragment extends Fragment implements View.OnClickListener {
    Fragment nextFrag;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_menu, container, false);

       // FrameLayout FrameLayout = root.findViewById(R.id.frmlayout);

        RelativeLayout versionfrag = root.findViewById(R.id.version);
        versionfrag.setOnClickListener(this);
        RelativeLayout cpufrag = root.findViewById(R.id.cpu);
        cpufrag.setOnClickListener(this);

        return root;
    }

    public void onClick(View view){
        switch (view.getId()) {
            case R.id.version:

                Navigation.findNavController(view).navigate(R.id.action_menu_to_version);
                break;
            case R.id.cpu:
                Navigation.findNavController(view).navigate(R.id.action_menu_to_cpulist);
                break;
        }

    }
}