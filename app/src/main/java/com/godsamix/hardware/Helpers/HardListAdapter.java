package com.godsamix.hardware.Helpers;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.godsamix.hardware.Controllers.HardListController;
import com.godsamix.hardware.R;
import com.squareup.picasso.Picasso;
import com.godsamix.hardware.hardwareItemsListFragment;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

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

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_hardware_specs, null);


        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.setElevation(20);
        TextView txt = popupView.findViewById(R.id.popmsg);
        // inflate linearlayout in the popview
        LinearLayout linear = popupView.findViewById(R.id.linearlay);
        TextView textView = new TextView(context);
        textView.setText(holder.name.getText());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear.addView(textView);
            txt.setText(holder.code.getText());
                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

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