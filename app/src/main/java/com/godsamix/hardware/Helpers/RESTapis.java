package com.godsamix.hardware.Helpers;

import com.godsamix.hardware.Controllers.HardListController;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RESTapis {
// those are the rest api methods list for retro2

  //  @GET(".")
  //   Call<List<HardListController>> getUsers();

    @GET("hardware/cpuslist")
    Call<List<HardListController>> getCpus(@Query("pagestart") int pagestart, @Query("pagesize") int pagesize);
    @GET("hardware/motherboardslist")
    Call<List<HardListController>> getBoards(@Query("pagestart") int pagestart, @Query("pagesize") int pagesize);
    @GET("hardware/vgaslist")
    Call<List<HardListController>> getVgas(@Query("pagestart") int pagestart, @Query("pagesize") int pagesize);

    @GET("hardware/cpusearch/{name}")
    Call<List<HardListController>> getCpusSearch(@Path("name") String name, @Query("pagestart") int pagestart, @Query("pagesize") int pagesize);

    @GET("hardware/cpu/{Code}")
    Call<List<HardListController>> getOneCpu(@Path("Code") String Code);

}
