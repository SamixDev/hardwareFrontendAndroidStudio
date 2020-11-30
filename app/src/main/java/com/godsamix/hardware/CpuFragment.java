package com.godsamix.hardware;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class CpuFragment extends Fragment {
    private final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private List<HardListController> viewlist = new ArrayList<>();
    private HardListAdapter cpuAdapter;
    private List<HardListController> cpuList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cpu, container, false);
       //  final TextView textView = root.findViewById(R.id.test);
        final Button btn = root.findViewById(R.id.buttontest);
        recyclerView = root.findViewById(R.id.recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        cpuAdapter = new HardListAdapter(getContext(), viewlist);
        recyclerView.setAdapter(cpuAdapter);

        getCpus();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"btn working",
                        Toast.LENGTH_LONG).show();
                viewlist.clear(); // clear list
                cpuAdapter.notifyDataSetChanged();
                getCpus();
            }
        });


        return root;
    }
    private void getCpus(){
        RESTapis RESTapis = RetrofitService.createService(RESTapis.class);
        Call<List<HardListController>> call = RESTapis.getCpus();
        call.enqueue(new Callback<List<HardListController>>() {
            @Override
            public void onResponse(Call<List<HardListController>> call, Response<List<HardListController>> response) {
                if(response.isSuccessful()) {
                    for(HardListController procc: response.body()){
                        viewlist.add(procc);
                    }
                    cpuAdapter.notifyDataSetChanged();
                }else{
                    Log.e(TAG, response.message());
                }
            }
            @Override
            public void onFailure(Call<List<HardListController>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }
}
