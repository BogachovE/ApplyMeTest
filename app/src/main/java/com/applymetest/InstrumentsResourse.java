package com.applymetest;

import com.applymetest.Models.Instrument;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Singletones.InstrumentsRepo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bogachov on 08.08.17.
 */

public class InstrumentsResourse implements InstrumentCreator {
    private static Api api;
    ArrayList<Instrument> instrumentsArray = new ArrayList<>();
    private Retrofit retrofit;

     public void  createInstruments() {
         load();
    }

    public void load(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://www.umori.li/") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(Api.class);


        api.getData().enqueue(new Callback<ArrayList<Instrument>>() {
            @Override
            public void onResponse(Call<ArrayList<Instrument>> call, Response<ArrayList<Instrument>> response) {
                InstrumentsRepo.getInstance().setInstrumentsArray(response.body());

            }

            @Override
            public void onFailure(Call<ArrayList<Instrument>> call, Throwable t) {

            }
        });
    }
}
