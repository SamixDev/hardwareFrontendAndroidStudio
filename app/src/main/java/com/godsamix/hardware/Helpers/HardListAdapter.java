package com.godsamix.hardware.Helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.godsamix.hardware.Controllers.HardListController;
import com.godsamix.hardware.R;
import com.squareup.picasso.Picasso;
import com.godsamix.hardware.hardwareItemsListFragment;
import java.util.List;

public class HardListAdapter extends RecyclerView.Adapter<HardListAdapter.ViewHolder> {
    private List<HardListController> hard;
    private Context context;


    public HardListAdapter(Context context, List<HardListController> hardware){
        this.hard = hardware;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView code, name, manuf;
        private ImageView img;
        public ViewHolder(View itemView) {
            super(itemView);
            code = (TextView) itemView.findViewById(R.id.code);
            name = (TextView) itemView.findViewById(R.id.name);
            manuf = (TextView) itemView.findViewById(R.id.manuf);
            img = (ImageView) itemView.findViewById(R.id.img);
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
                Toast.makeText(context,"item : "+lst.getCode() + " "+ lst.getName()+ " "+ lst.getImage(),Toast.LENGTH_SHORT).show();
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