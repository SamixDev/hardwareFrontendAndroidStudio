package com.godsamix.hardware;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.content.Context.MODE_PRIVATE;

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
        Call<List<HardListController>> call;
        switch (Type){
            case "cpu":
      call = RESTapis.getCpusSearch(txt,pagenumber,pagesize,"Bearer "+idToken);
                break;
            case "vga":
                call = RESTapis.getVgasSearch(txt,pagenumber,pagesize,"Bearer "+idToken);
                break;
            case "board":
                call = RESTapis.getBoardsSearch(txt,pagenumber,pagesize,"Bearer "+idToken);
                break;
            default:
                call = RESTapis.getCpusSearch(txt,pagenumber,pagesize,"Bearer "+idToken);
        }

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
                   // Log.e(TAG, response.message());
                }
                if (itemscount == itemscountprevious){
                    endresults = true;
                }else{
                    itemscountprevious = itemscount;
                }
            }
            @Override
            public void onFailure(Call<List<HardListController>> call, Throwable t) {
             //   Log.e(TAG, t.getMessage());
            }
        });
    }

    private void getHard(int pagenumber, int pagesize,String Type){
        RESTapis RESTapis = RetrofitService.createService(RESTapis.class);
        Call<List<HardListController>> call;
        Log.e("type ", Type);
        switch (Type){
            case "cpu":
                call = RESTapis.getCpus(pagenumber,pagesize,"Bearer "+idToken);
                break;
            case "vga":
                call = RESTapis.getVgas(pagenumber,pagesize,"Bearer "+idToken);
                break;
            case "board":
                call = RESTapis.getBoards(pagenumber,pagesize,"Bearer "+idToken);
                break;
            default:
                call = RESTapis.getCpus(pagenumber,pagesize,"Bearer "+idToken);
        }
        call.enqueue(new Callback<List<HardListController>>() {
            @Override
            public void onResponse(Call<List<HardListController>> call, Response<List<HardListController>> response) {
                Log.e("res", response.message());
                if(response.isSuccessful()) {
                    for(HardListController procc: response.body()){
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
            public void onFailure(Call<List<HardListController>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }
}
