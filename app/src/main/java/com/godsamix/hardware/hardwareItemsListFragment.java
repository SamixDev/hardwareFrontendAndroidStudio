package com.godsamix.hardware;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.godsamix.hardware.Helpers.HardListAdapter;
import com.godsamix.hardware.Controllers.HardListController;

import com.godsamix.hardware.Helpers.RESTapis;
import com.godsamix.hardware.Helpers.RetrofitService;
import com.google.android.material.textfield.TextInputEditText;

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
    public static String args;
    public boolean endresults = false;
    public int pagenumber = 0;
    public int pagesize = 5;
    public int itemscount =1;
    public int itemscountprevious =0;
    public ImageView searchBtn;
    public TextInputEditText txtToSearch;
    public String searchableText;
    public boolean isSearch = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_hardware_items_list, container, false);

        searchBtn = root.findViewById(R.id.searchbutton);
        txtToSearch = root.findViewById(R.id.searchinput);

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
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
                if(!endresults)
                {
                    if(linearLayoutManager.findLastCompletelyVisibleItemPosition() == viewlist.size() - 1)
                    {
                        hardAdapter.notifyDataSetChanged();
                        if (!isSearch){
                      //      Toast.makeText(getContext(), "no txt ",Toast.LENGTH_SHORT).show();
                            switch (args) {
                                case "cpu":
                                    getCpus(pagenumber, pagesize);
                                    pagenumber += pagesize;
                                    break;
                                case "vga":
                                    getVgas(pagenumber, pagesize);
                                    pagenumber += pagesize;
                                    break;
                                case "board":
                                    getBoards(pagenumber, pagesize);
                                    pagenumber += pagesize;
                                    break;
                            }
                        }else {
                       //     Toast.makeText(getContext(), "txt is " + searchableText,Toast.LENGTH_SHORT).show();
                            switch(args) {
                                case "cpu":
                                    getCpusSearch(searchableText,pagenumber,pagesize);
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
                }
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(getContext(), "searching for  " + txtToSearch.getText(),Toast.LENGTH_SHORT).show();
                if (TextUtils.isEmpty(txtToSearch.getText().toString())) {
                //    Toast.makeText(getContext(), "empty ",Toast.LENGTH_SHORT).show();
                    isSearch = false;
                    viewlist.clear(); // clear list
                    pagenumber = 0;
                    pagesize = 5;
                    itemscount =1;
                    itemscountprevious =0;
                    searchableText = "";
                    endresults = false;
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
                }else{
                    isSearch = true;
                    viewlist.clear(); // clear list
                    pagenumber = 0;
                   pagesize = 5;
                  itemscount =1;
                   itemscountprevious =0;
                    endresults = false;
                    searchableText = txtToSearch.getText().toString();
                    hardAdapter.notifyDataSetChanged();
              //      Toast.makeText(getContext(), "not empty",Toast.LENGTH_SHORT).show();
                    switch(args) {
                        case "cpu":
                            getCpusSearch(searchableText,pagenumber,pagesize);
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
    private void getCpusSearch(String txt, int pagenumber, int pagesize){
        RESTapis RESTapis = RetrofitService.createService(RESTapis.class);
        Call<List<HardListController>> call = RESTapis.getCpusSearch(txt,pagenumber,pagesize);
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
