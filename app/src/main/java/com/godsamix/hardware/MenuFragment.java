package com.godsamix.hardware;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.google.android.material.navigation.NavigationView;

public class MenuFragment extends Fragment implements View.OnClickListener {

    RelativeLayout versionfrag;
    RelativeLayout cpufrag;
    RelativeLayout vgafrag;
    RelativeLayout aboutfrag;
    RelativeLayout boardfrag;
    RelativeLayout buildfrag;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_menu, container, false);

        versionfrag = root.findViewById(R.id.version);
        cpufrag = root.findViewById(R.id.cpu);
        vgafrag = root.findViewById(R.id.vga);
        aboutfrag = root.findViewById(R.id.about);
        boardfrag = root.findViewById(R.id.board);
        //buildfrag = root.findViewById(R.id.build);

        versionfrag.setOnClickListener(this);
        cpufrag.setOnClickListener(this);
        vgafrag.setOnClickListener(this);
        aboutfrag.setOnClickListener(this);
        boardfrag.setOnClickListener(this);
        //buildfrag.setOnClickListener(this);

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
          //      Navigation.findNavController(view).navigate(R.id.action_menu_to_build);
                break;
            case R.id.board:
                Navigation.findNavController(view).navigate(R.id.action_menu_to_boardlist);
                break;
        }

    }
}