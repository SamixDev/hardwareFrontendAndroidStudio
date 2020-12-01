package com.godsamix.hardware;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.godsamix.hardware.Helpers.HardListAdapter;
import com.godsamix.hardware.Controllers.HardListController;

import com.godsamix.hardware.Helpers.RESTapis;
import com.godsamix.hardware.Helpers.RetrofitService;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class hardwareItemsListFragment extends Fragment {

    private final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private List<HardListController> viewlist = new ArrayList<>();
    private HardListAdapter hardAdapter;
    private List<HardListController> cpuList = new ArrayList<>();
    public static String args;
    public boolean endresults = false;
    public int pagenumber = 0;
    public int pagesize = 5;
    public int itemscount =1;
    public int itemscountprevious =0;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_hardware_items_list, container, false);
        final Button btn = root.findViewById(R.id.buttontest);
        recyclerView = root.findViewById(R.id.recyclerview);
        args = this.getArguments().getString("listType");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        hardAdapter = new HardListAdapter(getContext(), viewlist);
        recyclerView.setAdapter(hardAdapter);
        switch(args) {
            case "cpu":
                getCpus(pagenumber,pagesize);
                pagenumber += pagesize ;
                break;
            case "vga":
                getVgas(pagenumber,pagesize);
                pagenumber += pagesize ;
                break;
            case "board":
                getBoards(pagenumber,pagesize);
                pagenumber += pagesize ;
                break;
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(!endresults){
                hardAdapter.notifyDataSetChanged();
                switch(args) {
                    case "cpu":
                        getCpus(pagenumber,pagesize);
                        pagenumber += pagesize ;
                        break;
                    case "vga":
                        getVgas(pagenumber,pagesize);
                        pagenumber += pagesize ;
                        break;
                    case "board":
                        getBoards(pagenumber,pagesize);
                        pagenumber += pagesize ;
                        break;
                }
            }


            }
        });

        return root;
    }
    private void getCpus(int pagenumber, int pagesize){
        RESTapis RESTapis = RetrofitService.createService(RESTapis.class);
        Call<List<HardListController>> call = RESTapis.getCpus(pagenumber,pagesize);
        call.enqueue(new Callback<List<HardListController>>() {
            @Override
            public void onResponse(Call<List<HardListController>> call, Response<List<HardListController>> response) {
                if(response.isSuccessful()) {
                    for(HardListController procc: response.body()){
                        viewlist.add(procc);
                        itemscount = viewlist.size();

                    }
                    hardAdapter.notifyDataSetChanged();
                }else{
                    Log.e(TAG, response.message());
                }
                Toast.makeText(getContext()," itemcount" + itemscount, Toast.LENGTH_SHORT).show();
                if (itemscount == itemscountprevious){
                    endresults = true;
                }else{
                    itemscountprevious = itemscount;
                }
            }
            @Override
            public void onFailure(Call<List<HardListController>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }
    private void getBoards(int pagenumber, int pagesize){
        RESTapis RESTapis = RetrofitService.createService(RESTapis.class);
        Call<List<HardListController>> call = RESTapis.getBoards(pagenumber,pagesize);
        call.enqueue(new Callback<List<HardListController>>() {
            @Override
            public void onResponse(Call<List<HardListController>> call, Response<List<HardListController>> response) {
                if(response.isSuccessful()) {
                    for(HardListController procc: response.body()){
                        viewlist.add(procc);
                        itemscount = viewlist.size();
                    }
                    hardAdapter.notifyDataSetChanged();
                }else{
                    Log.e(TAG, response.message());
                }
                Toast.makeText(getContext()," itemcount" + itemscount, Toast.LENGTH_SHORT).show();
                if (itemscount == itemscountprevious){
                    endresults = true;
                }else{
                    itemscountprevious = itemscount;
                }
            }
            @Override
            public void onFailure(Call<List<HardListController>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }
    private void getVgas(int pagenumber, int pagesize){
        RESTapis RESTapis = RetrofitService.createService(RESTapis.class);
        Call<List<HardListController>> call = RESTapis.getVgas(pagenumber,pagesize);
        call.enqueue(new Callback<List<HardListController>>() {
            @Override
            public void onResponse(Call<List<HardListController>> call, Response<List<HardListController>> response) {
                if(response.isSuccessful()) {
                    for(HardListController procc: response.body()){
                        viewlist.add(procc);
                        itemscount = viewlist.size();
                    }
                    hardAdapter.notifyDataSetChanged();
                }else{
                    Log.e(TAG, response.message());
                }
                Toast.makeText(getContext()," itemcount" + itemscount, Toast.LENGTH_SHORT).show();
                if (itemscount == itemscountprevious){
                    endresults = true;
                }else{
                    itemscountprevious = itemscount;
                }
            }
            @Override
            public void onFailure(Call<List<HardListController>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }
}
