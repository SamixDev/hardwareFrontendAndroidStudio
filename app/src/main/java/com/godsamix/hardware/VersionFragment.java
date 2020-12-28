package com.godsamix.hardware;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.godsamix.hardware.BuildConfig;
import com.godsamix.hardware.R;

public class VersionFragment extends Fragment {
    

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_version, container, false);

        //set Version nb to textView
        TextView ver = root.findViewById(R.id.ver);
        ver.setText("Version " + BuildConfig.VERSION_NAME);

        //make privacy TextView clickable
        TextView privacy = root.findViewById(R.id.privacy);
        privacy.setMovementMethod(LinkMovementMethod.getInstance());
        

        return root;
    }
}