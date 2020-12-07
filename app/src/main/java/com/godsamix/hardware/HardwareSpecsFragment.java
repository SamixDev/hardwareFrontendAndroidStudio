package com.godsamix.hardware;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.godsamix.hardware.Helpers.RESTapis;
import com.godsamix.hardware.Helpers.RetrofitService;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

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
            @SuppressLint("ResourceType")
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccessful()) {
                    String res = new Gson().toJson(response.body());
                    try {
                        JSONArray jsonArray = new JSONArray(res);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        for(int i = 0; i<jsonObject.length(); i++){
                            Log.e(TAG, "key = " + jsonObject.names().getString(i) + ", value = " + jsonObject.get(jsonObject.names().getString(i)));
                           if (jsonObject.names().getString(i).equals("Image")){
                               ImageView img = new ImageView(getContext());
                               int width = 250;
                               int height = 250;
                               LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
                               parms.gravity= Gravity.CENTER;
                               img.setLayoutParams(parms);
                               img.setPadding(0,2,0,20);
                               linearLayout.addView(img,0);
                               Picasso.get().load(jsonObject.get(jsonObject.names().getString(i)).toString()).into(img);

                           }else{
                               TextView textView = new TextView(getContext());
                               textView.setPadding(10,10,10,10);
                               textView.setText(jsonObject.names().getString(i) + ": " +jsonObject.get(jsonObject.names().getString(i)).toString());
                               textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                               Drawable drawable = ResourcesCompat.getDrawable(getResources(),R.drawable.roundedcornerslist,null);
                               textView.setBackground(drawable);
                               LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                               lp.setMargins(0, 0, 0, 20);
                               textView.setLayoutParams(lp);
                               linearLayout.addView(textView);

//                               android:layout_marginRight="12sp"
//                               android:layout_marginLeft="10sp"
//                               android:layout_marginBottom="12sp"
                           }
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