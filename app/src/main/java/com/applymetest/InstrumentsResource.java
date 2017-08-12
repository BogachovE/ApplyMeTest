package com.applymetest;

import android.content.Context;

import com.applymetest.Mappers.InstrumentsResponseMapper;
import com.applymetest.Models.Instrument;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class InstrumentsResource implements InstrumentCreator, AddChoose {

    private Deferred<ArrayList<Instrument>, String, String> getDeferred = new DeferredObject<>();
    private Promise<ArrayList<Instrument>, String, String> getPromise() {
        return getDeferred.promise();
    }
    private Deferred<String, String, String> postDeferred = new DeferredObject<>();
    private Promise<String, String, String> postPromise() {
        return postDeferred.promise();
    }


    public Promise<ArrayList<Instrument>, String, String> createInstruments(Context context){
        String baseURL = context.getString(R.string.baseURL);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        api.getData().enqueue(new Callback<HashMap>() {
            @Override
            public void onResponse(Call<HashMap> call, Response<HashMap> response) {
                ArrayList<Instrument> instrumentsArray = InstrumentsResponseMapper.map(response.body());
                getDeferred.resolve(instrumentsArray);
            }

            @Override
            public void onFailure(Call<HashMap> call, Throwable t) {
                getDeferred.reject("Failure");
            }
        });

        return getPromise();
    }
    public Promise<String, String, String> addChoose(Context context, String name) {

        String baseURL = context.getString(R.string.baseURL);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        api.addChoose(name).enqueue(new Callback<HashMap>() {
            @Override
            public void onResponse(Call<HashMap> call, Response<HashMap> response) {
                postDeferred.resolve("Post status is " + response.body().get("status"));
            }

            @Override
            public void onFailure(Call<HashMap> call, Throwable t) {
                postDeferred.reject("Failure");
            }
        });

        return postPromise();
    }

    @Override
    public void updateDeferred() {
        postDeferred = new DeferredObject<>();
    }


}
