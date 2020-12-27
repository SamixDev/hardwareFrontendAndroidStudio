package com.godsamix.hardware.Helpers;

import android.content.SharedPreferences;

import com.godsamix.hardware.Controllers.HardListController;
import com.godsamix.hardware.Controllers.responseController;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RESTapis {
// those are the rest api methods list for retro2

    @GET("hardware/cpuslist")
    Call<responseController> getCpus(@Query("pagestart") int pagestart, @Query("pagesize") int pagesize,@Header("Authorization") String authorization);
    @GET("hardware/motherboardslist")
    Call<responseController> getBoards(@Query("pagestart") int pagestart, @Query("pagesize") int pagesize,@Header("Authorization") String authorization);
    @GET("hardware/vgaslist")
    Call<responseController> getVgas(@Query("pagestart") int pagestart, @Query("pagesize") int pagesize,@Header("Authorization") String authorization);

    @GET("hardware/cpusearch")
    Call<responseController> getCpusSearch(@Query("name") String name, @Query("pagestart") int pagestart, @Query("pagesize") int pagesize,@Header("Authorization") String authorization);
    @GET("hardware/boardsearch")
    Call<responseController> getBoardsSearch(@Query("name") String name, @Query("pagestart") int pagestart, @Query("pagesize") int pagesize,@Header("Authorization") String authorization);
    @GET("hardware/vgasearch")
    Call<responseController> getVgasSearch(@Query("name") String name, @Query("pagestart") int pagestart, @Query("pagesize") int pagesize,@Header("Authorization") String authorization);

    @GET("hardware/cpu")
    Call<Object> getOneCpu(@Query("code") String Code,@Header("Authorization") String authorization);
    @GET("hardware/vga")
    Call<Object> getOneVga(@Query("code") String Code,@Header("Authorization") String authorization);
    @GET("hardware/motherboard")
    Call<Object> getOneBoard(@Query("code") String Code,@Header("Authorization") String authorization);

    @GET("user/phoneSignInUp/")
    Call<Object> signInUp(@Query("login") String login, @Query("fullname") String fullname);

}
