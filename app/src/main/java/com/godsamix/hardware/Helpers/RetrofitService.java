package com.godsamix.hardware.Helpers;
import com.godsamix.hardware.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitService {
    // this is the retrofit class that makes the calls
    private static final String BASE_API_URL = "http://192.168.1.4:3000/api/";
    private static Retrofit retrofit = null;
    private static Gson gson = new GsonBuilder().create();
    private static HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private static OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor);
    private static OkHttpClient okHttpClient = okHttpClientBuilder.build();
    public static <T> T createService(Class<T> serviceClass){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_API_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit.create(serviceClass);
    }

}