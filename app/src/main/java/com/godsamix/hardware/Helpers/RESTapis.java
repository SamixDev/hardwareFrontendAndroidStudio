package com.godsamix.hardware.Helpers;

import com.godsamix.hardware.Controllers.HardListController;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RESTapis {
// those are the rest api methods list for retro2

  //  @GET(".")
  //   Call<List<HardListController>> getUsers();

    @GET("hardware/cpuslist")
    Call<List<HardListController>> getCpus();

    @GET("hardware/cpu/")
    Call<List<HardListController>> getCpu(@Query("id") String cpuid);

}
