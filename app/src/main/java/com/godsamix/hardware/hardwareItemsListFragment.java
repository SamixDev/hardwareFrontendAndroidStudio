package com.godsamix.hardware;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.godsamix.hardware.Controllers.responseController;
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

import static android.content.Context.MODE_PRIVATE;

public class hardwareItemsListFragment extends Fragment {

    private final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private final List<HardListController> viewlist = new ArrayList<HardListController>();

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
    public static String idToken;
    //shared prefs
    public  static SharedPreferences sharedPreferences;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_hardware_items_list, container, false);

        searchBtn = root.findViewById(R.id.searchbutton);
        txtToSearch = root.findViewById(R.id.searchinput);

        //shared prefs init
        sharedPreferences = this.getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        //personEmail = sharedPreferences.getString("email", "");
        idToken = sharedPreferences.getString("token", "");

        //warning not signed in
        TextView warning = root.findViewById(R.id.notifier);
        if(idToken==""){
            warning.setText("⚠ Please Sign In to view content");
            warning.setVisibility(View.VISIBLE);
        }else{
            warning.setVisibility(View.INVISIBLE);
        }


        recyclerView = root.findViewById(R.id.recyclerview);
        args = this.getArguments().getString("listType");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        hardAdapter = new HardListAdapter(getContext(), viewlist);
        recyclerView.setAdapter(hardAdapter);
        getHard(pagenumber, pagesize,args);
        pagenumber += pagesize ;
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
                            getHard(pagenumber, pagesize,args);
                            pagenumber += pagesize;
                        }else {
                            getHardSearch(searchableText,pagenumber,pagesize,args);
                            pagenumber += pagesize ;
                        }
                    }
                }
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(txtToSearch.getText().toString())) {
                    isSearch = false;
                    viewlist.clear(); // clear list
                    pagenumber = 0;
                    pagesize = 5;
                    itemscount =1;
                    itemscountprevious =0;
                    searchableText = "";
                    endresults = false;
                    hardAdapter.notifyDataSetChanged();
                    getHard(pagenumber, pagesize,args);
                    pagenumber += pagesize ;
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
                    getHardSearch(searchableText,pagenumber,pagesize,args);
                    pagenumber += pagesize ;
                }

            }
        });


        return root;
    }
    private void getHardSearch(String txt, int pagenumber, int pagesize,String Type){
        RESTapis RESTapis = RetrofitService.createService(RESTapis.class);
        Call<responseController> call;
        switch (Type){
            case "vga":
                call = RESTapis.getVgasSearch(txt,pagenumber,pagesize,"Bearer "+idToken);
                break;
            case "board":
                call = RESTapis.getBoardsSearch(txt,pagenumber,pagesize,"Bearer "+idToken);
                break;
            case "cpu":
            default:
                call = RESTapis.getCpusSearch(txt,pagenumber,pagesize,"Bearer "+idToken);
        }

        call.enqueue(new Callback<responseController>() {
            @Override
            public void onResponse(Call<responseController> call, Response<responseController> response) {
                if(response.isSuccessful()) {
                    for(HardListController procc: response.body().getMessage()){
                        viewlist.add(procc);
                        itemscount = viewlist.size();

                    }
                    hardAdapter.notifyDataSetChanged();
                }else{
                   // Log.e(TAG, response.message());
                }
                if (itemscount == itemscountprevious){
                    endresults = true;
                }else{
                    itemscountprevious = itemscount;
                }
            }
            @Override
            public void onFailure(Call<responseController> call, Throwable t) {
             //   Log.e(TAG, t.getMessage());
            }
        });
    }

    private void getHard(int pagenumber, int pagesize,String Type){
        RESTapis RESTapis = RetrofitService.createService(RESTapis.class);
        Call<responseController> call;
      //  Log.e("type ", Type);
        switch (Type){
            case "vga":
                call = RESTapis.getVgas(pagenumber,pagesize,"Bearer "+idToken);
                break;
            case "board":
                call = RESTapis.getBoards(pagenumber,pagesize,"Bearer "+idToken);
                break;
            case "cpu":
            default:
                call = RESTapis.getCpus(pagenumber,pagesize,"Bearer "+idToken);
        }
        call.enqueue(new Callback<responseController>() {
            @Override
            public void onResponse(Call<responseController> call, Response<responseController> response) {
             //   Log.e("res", response.body().getMessage().toString());
//                for (HardListController rr: response.body().getMessage()){
//                    Log.e("res2", rr.getName());
//                }
                if(response.isSuccessful()) {
                    for(HardListController procc: response.body().getMessage()){
                        viewlist.add(procc);
                        itemscount = viewlist.size();

                    }
                    hardAdapter.notifyDataSetChanged();
                }else{
                //    Log.e(TAG, response.message());
                }
                if (itemscount == itemscountprevious){
                    endresults = true;
                }else{
                    itemscountprevious = itemscount;
                }
            }
            @Override
            public void onFailure(Call<responseController> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }
}
