package com.godsamix.hardware.Helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.godsamix.hardware.Controllers.CpuController;
import com.godsamix.hardware.R;
import java.util.List;

public class CpuAdapter extends RecyclerView.Adapter<CpuAdapter.ViewHolder> {
    private List<CpuController> cpus;
    private Context context;

    public CpuAdapter(Context context, List<CpuController> processors){
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
        CpuController user = cpus.get(position);
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