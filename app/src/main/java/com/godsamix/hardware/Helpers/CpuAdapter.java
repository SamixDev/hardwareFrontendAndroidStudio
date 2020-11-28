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
    private List<CpuController> users;
    private Context context;

    public CpuAdapter(Context context, List<CpuController> movies){
        this.users = movies;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView id, name, email;
        public ViewHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.title);
            name = (TextView) itemView.findViewById(R.id.director);
            email = (TextView) itemView.findViewById(R.id.description);
        }
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CpuController user = users.get(position);
        holder.id.setText(user.getId());
        holder.name.setText(user.getName());
        holder.email.setText(user.getEmail());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cpu_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}