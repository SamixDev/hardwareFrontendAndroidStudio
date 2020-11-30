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

import java.util.List;

public class HardListAdapter extends RecyclerView.Adapter<HardListAdapter.ViewHolder> {
    private List<HardListController> cpus;
    private Context context;

    public HardListAdapter(Context context, List<HardListController> processors){
        this.cpus = processors;
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
        HardListController lst = cpus.get(position);
        holder.code.setText(lst.getCode());
        holder.name.setText(lst.getName());
        holder.manuf.setText(lst.getManufacturer());
        //https://www.amd.com/system/files/styles/992px/private/2020-09/616656-amd-ryzen-5-5000-series-PIB-fan-1260x709.png?itok=g0FNgeyd
        Toast.makeText(context,"img : "+lst.getImage(),Toast.LENGTH_LONG).show();
        if (lst.getImage() != null){
         //   String imageurl = lst.getImage();

            Picasso.get().load(lst.getImage()).into(holder.img);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(context,"Position : "+holder.code.getText(),Toast.LENGTH_LONG).show();
                Toast.makeText(context,"Position : "+lst.getImage(),Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cpu_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return cpus.size();
    }
}