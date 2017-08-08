package com.applymetest;

import com.applymetest.Models.Instrument;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by bogachov on 08.08.17.
 */

public interface Api {
    @GET("/poll-results")
    Call<ArrayList<Instrument>> getData();

}
