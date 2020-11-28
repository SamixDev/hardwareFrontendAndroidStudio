package com.godsamix.hardware;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.godsamix.hardware.Helpers.CpuAdapter;
import com.godsamix.hardware.Controllers.CpuController;

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
    private List<CpuController> users = new ArrayList<>();
    private CpuAdapter usersAdapter;
    private List<CpuController> movieList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cpu, container, false);
       //  final TextView textView = root.findViewById(R.id.test);
        final Button btn = root.findViewById(R.id.buttontest);
        recyclerView = root.findViewById(R.id.recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        usersAdapter = new CpuAdapter(getContext(), users);
        recyclerView.setAdapter(usersAdapter);

        getUsers();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"btn working",
                        Toast.LENGTH_LONG).show();
                users.clear(); // clear list
                usersAdapter.notifyDataSetChanged();
                getUsers();
            }
        });

        return root;
    }
    private void getUsers(){
        RESTapis RESTapis = RetrofitService.createService(RESTapis.class);
        Call<List<CpuController>> call = RESTapis.getUsers();
        call.enqueue(new Callback<List<CpuController>>() {
            @Override
            public void onResponse(Call<List<CpuController>> call, Response<List<CpuController>> response) {
                if(response.isSuccessful()) {
                    for(CpuController movie: response.body()){
                        users.add(movie);
                    }
                    usersAdapter.notifyDataSetChanged();
                }else{
                    Log.e(TAG, response.message());
                }
            }
            @Override
            public void onFailure(Call<List<CpuController>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }
}
