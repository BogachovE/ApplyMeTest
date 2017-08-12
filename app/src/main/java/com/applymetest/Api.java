package com.applymetest;

import com.applymetest.Models.Instrument;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by bogachov on 08.08.17.
 */

public interface Api {
    @GET("/poll-results")
    Call<HashMap> getData();

    @POST("/submit-poll")
    Call<HashMap> addChoose(@Body String name);

}
