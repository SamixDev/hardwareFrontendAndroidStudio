package com.godsamix.hardware;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.godsamix.hardware.Controllers.HardListController;
import com.godsamix.hardware.Helpers.RESTapis;
import com.godsamix.hardware.Helpers.RetrofitService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class HardwareSpecsFragment extends Fragment {
    public static String args_code;
    public static String args_type;
    TextView t3;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_hardware_specs, container, false);
        args_code = this.getArguments().getString("HardwareCode");
        args_type = this.getArguments().getString("HardwareType");

        getOneCpu(args_code,args_type);




        return root;
    }

    private void getOneCpu(String code, String Type){
        RESTapis RESTapis = RetrofitService.createService(RESTapis.class);
        Call call;
        switch (Type){
            case "cpu":
                call = RESTapis.getOneCpu(code);
                break;
            case "vga":
                call = RESTapis.getOneVga(code);
                break;
            case "board":
                call = RESTapis.getOneBoard(code);
                break;
            default:
                call = RESTapis.getOneCpu(code);
        }
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccessful()) {
                    String res = new Gson().toJson(response.body());
                    try {
                        JSONArray jsonArray = new JSONArray(res);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        for(int i = 0; i<jsonObject.length(); i++){
                            Log.e(TAG, "key = " + jsonObject.names().getString(i) + ", value = " + jsonObject.get(jsonObject.names().getString(i)));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                }
            }
            @Override
            public void onFailure(Call call, Throwable t) {
             //   Log.e(TAG, t.getMessage());
                t3.setText(t.getMessage());
            }
        });
    }
}