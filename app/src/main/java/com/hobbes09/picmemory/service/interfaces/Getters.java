package com.hobbes09.picmemory.service.interfaces;

import com.hobbes09.picmemory.model.pojos.PicFeed;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by hobbes09 on 08/05/17.
 */

public interface Getters {

    @GET("/")
    Call<PicFeed> getPicsFeed(@Query("s") String search, @Query("page") String page);
}
