package com.applymetest;

import android.content.Context;

import com.applymetest.Models.Instrument;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

import java.util.ArrayList;

import Singletons.InstrumentsRepo;

/**
 * Created by bogachov on 08.08.17.
 */

public class InstrumentsLocalCreator implements InstrumentCreator {
    private final Deferred<ArrayList<Instrument>, String, String> deferred = new DeferredObject<>();
    private Promise<ArrayList<Instrument>, String, String> promise() {
        return deferred.promise();
    }

     public Promise<ArrayList<Instrument>, String, String> createInstruments(Context context){
     ArrayList<Instrument> instrumentsArray = new ArrayList<>();
        instrumentsArray.add( new Instrument(R.string.bass,R.color.bass,1,R.drawable.bass,0f));
        instrumentsArray.add( new Instrument(R.string.banjo,R.color.banjo,2,R.drawable.banjo,0f));
        instrumentsArray.add( new Instrument(R.string.guitar,R.color.guitar,50,R.drawable.guitar,0f));
        instrumentsArray.add( new Instrument(R.string.electric,R.color.electric,50, R.drawable.electric,0f));
         InstrumentsRepo.getInstance().setInstrumentsArray(instrumentsArray);
         deferred.resolve(instrumentsArray);

         return promise();
    }

}
