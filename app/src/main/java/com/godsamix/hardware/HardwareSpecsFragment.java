package com.godsamix.hardware;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
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
import static android.content.Context.MODE_PRIVATE;

public class HardwareSpecsFragment extends Fragment {
    public static String args_code;
    public static String args_type;
    LinearLayout linearLayout;
    public static String idToken;
    //shared prefs
    public  static SharedPreferences sharedPreferences;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_hardware_specs, container, false);

        //shared prefs init
        sharedPreferences = this.getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        //personEmail = sharedPreferences.getString("email", "");
        idToken = sharedPreferences.getString("token", "");
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
            case "vga":
                call = RESTapis.getOneVga(code,"Bearer "+idToken);
                break;
            case "board":
                call = RESTapis.getOneBoard(code,"Bearer "+idToken);
                break;
            case "cpu":
            default:
                call = RESTapis.getOneCpu(code,"Bearer "+idToken);
        }
        call.enqueue(new Callback() {
            @SuppressLint("ResourceType")
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccessful()) {
                    String res = new Gson().toJson(response.body());
                    try {
                        JSONObject jsonOb = new JSONObject(res);
                        JSONArray jsonArray = jsonOb.getJSONArray("message");
                        JSONObject jsonObject =jsonArray.getJSONObject(0);

                        for(int i = 0; i<jsonObject.length(); i++){
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
                               textView.setText(jsonObject.names().getString(i) + ":\n" +jsonObject.get(jsonObject.names().getString(i)).toString());
                               textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                               Drawable drawable = ResourcesCompat.getDrawable(getResources(),R.drawable.roundedcornerslist,null);
                               textView.setBackground(drawable);
                               LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                               lp.setMargins(0, 0, 0, 20);
                               textView.setLayoutParams(lp);
                               linearLayout.addView(textView);

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
            }
        });
    }
}