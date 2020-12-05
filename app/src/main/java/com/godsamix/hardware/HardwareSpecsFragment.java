package com.godsamix.hardware;

import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.godsamix.hardware.Helpers.RESTapis;
import com.godsamix.hardware.Helpers.RetrofitService;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class HardwareSpecsFragment extends Fragment {
    public static String args_code;
    public static String args_type;
    LinearLayout linearLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_hardware_specs, container, false);
        args_code = this.getArguments().getString("HardwareCode");
        args_type = this.getArguments().getString("HardwareType");
        linearLayout = root.findViewById(R.id.linlay);
        getHardwareSpecs(args_code,args_type);


        return root;
    }

    private void getHardwareSpecs(String code, String Type){
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
                            TextView textView = new TextView(getContext());
                            textView.setPadding(10,10,10,10);
                            textView.setText(jsonObject.names().getString(i) + " :" +jsonObject.get(jsonObject.names().getString(i)).toString());
                            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                            linearLayout.addView(textView);
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
            }
        });
    }
}