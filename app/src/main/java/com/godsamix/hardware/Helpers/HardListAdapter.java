package com.godsamix.hardware.Helpers;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.godsamix.hardware.Controllers.HardListController;
import com.godsamix.hardware.Controllers.responseController;
import com.godsamix.hardware.R;
import com.squareup.picasso.Picasso;
import com.godsamix.hardware.hardwareItemsListFragment;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class HardListAdapter extends RecyclerView.Adapter<HardListAdapter.ViewHolder> {
    private final List<HardListController> hard;
    private final Context context;


    public HardListAdapter(Context context, List<HardListController> hardware){
        this.hard = hardware;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView code;
        private final TextView name;
        private final TextView manuf;
        private final ImageView img;
        public ViewHolder(View itemView) {
            super(itemView);
            code = itemView.findViewById(R.id.code);
            name = itemView.findViewById(R.id.name);
            manuf = itemView.findViewById(R.id.manuf);
            img = itemView.findViewById(R.id.img);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HardListController lst = hard.get(position);
        holder.code.setText(lst.getCode());
        holder.name.setText(lst.getName());
        holder.manuf.setText(lst.getManufacturer());

        if (lst.getImage() != null){
            Picasso.get().load(lst.getImage()).into(holder.img);
        }else{
            if(hardwareItemsListFragment.args.equals( "cpu")){
                holder.img.setImageResource(R.drawable.cpuempty);
            }else if(hardwareItemsListFragment.args.equals( "vga")){
                holder.img.setImageResource(R.drawable.vgaempty);
            }else if(hardwareItemsListFragment.args.equals( "board")){
                holder.img.setImageResource(R.drawable.boardempty);
            }
        }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("HardwareCode", holder.code.getText().toString());
                bundle.putString("HardwareType", hardwareItemsListFragment.args);
                Navigation.findNavController(v).navigate(R.id.action_nav_hardware_to_nav_hardware_specs,bundle);
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hardware_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return hard.size();
    }
}