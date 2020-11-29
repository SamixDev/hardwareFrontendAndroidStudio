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
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.godsamix.hardware.R;
import com.google.android.material.navigation.NavigationView;

public class MenuFragment extends Fragment implements View.OnClickListener {
    NavigationView navigationView;
    DrawerLayout drawer;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_menu, container, false);





        RelativeLayout versionfrag = root.findViewById(R.id.version);
        versionfrag.setOnClickListener(this);
        RelativeLayout cpufrag = root.findViewById(R.id.cpu);
        cpufrag.setOnClickListener(this);
        RelativeLayout vgafrag = root.findViewById(R.id.vga);
        vgafrag.setOnClickListener(this);
        RelativeLayout aboutfrag = root.findViewById(R.id.about);
        aboutfrag.setOnClickListener(this);
        RelativeLayout boardfrag = root.findViewById(R.id.board);
        boardfrag.setOnClickListener(this);
        RelativeLayout buildfrag = root.findViewById(R.id.build);
        buildfrag.setOnClickListener(this);
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
            case R.id.about:
                Navigation.findNavController(view).navigate(R.id.action_menu_to_about);
                break;
            case R.id.vga:
                Navigation.findNavController(view).navigate(R.id.action_menu_to_vgalist);
                break;
            case R.id.build:
                Navigation.findNavController(view).navigate(R.id.action_menu_to_build);
                break;
            case R.id.board:
                Navigation.findNavController(view).navigate(R.id.action_menu_to_boardlist);
                break;
        }

    }
}