package com.godsamix.hardware.Helpers;

import com.godsamix.hardware.Controllers.CpuController;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RESTapis {
// those are the rest api methods list for retro2
    @GET(".")
     Call<List<CpuController>> getUsers();

    @GET("hardware/cpuslist")
    Call<List<CpuController>> getCpus();

    @GET("cpu")
    Call<List<CpuController>> getCpu(@Query("id") String cpuid);

}
