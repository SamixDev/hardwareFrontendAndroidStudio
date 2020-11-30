package com.godsamix.hardware.Helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.godsamix.hardware.Controllers.HardListController;
import com.godsamix.hardware.R;
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
        public ViewHolder(View itemView) {
            super(itemView);
            code = (TextView) itemView.findViewById(R.id.code);
            name = (TextView) itemView.findViewById(R.id.name);
            manuf = (TextView) itemView.findViewById(R.id.manuf);
        }
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HardListController user = cpus.get(position);
        holder.code.setText(user.getCode());
        holder.name.setText(user.getName());
        holder.manuf.setText(user.getManufacturer());
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