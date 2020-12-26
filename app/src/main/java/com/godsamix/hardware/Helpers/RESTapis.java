package com.godsamix.hardware.Helpers;

import com.godsamix.hardware.Controllers.HardListController;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
    @GET("hardware/boardsearch/{name}")
    Call<List<HardListController>> getBoardsSearch(@Path("name") String name, @Query("pagestart") int pagestart, @Query("pagesize") int pagesize);
    @GET("hardware/vgasearch/{name}")
    Call<List<HardListController>> getVgasSearch(@Path("name") String name, @Query("pagestart") int pagestart, @Query("pagesize") int pagesize);

    @GET("hardware/cpu/{Code}")
    Call<Object> getOneCpu(@Path("Code") String Code);
    @GET("hardware/vga/{Code}")
    Call<Object> getOneVga(@Path("Code") String Code);
    @GET("hardware/motherboard/{Code}")
    Call<Object> getOneBoard(@Path("Code") String Code);

    @POST("user/signInUp/")
    Call<Object> signInUp(@Query("login") String login, @Query("password") String password, @Query("fullname") String fullname);

}
