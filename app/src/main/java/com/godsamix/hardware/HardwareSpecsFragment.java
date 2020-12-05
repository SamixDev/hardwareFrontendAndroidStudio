package com.godsamix.hardware;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.godsamix.hardware.R;

public class HardwareSpecsFragment extends Fragment {
    public static String args;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_hardware_specs, container, false);
        args = this.getArguments().getString("HardwareCode");
        TextView t = root.findViewById(R.id.textView);
        t.setText(args);


        return root;
    }
}