package com.applymetest;

import com.applymetest.Models.Instrument;

import java.util.ArrayList;

import Singletones.InstrumentsRepo;

/**
 * Created by bogachov on 08.08.17.
 */

public class InstrumentsLocalCreator implements InstrumentCreator {
     public void  createInstruments(){
     ArrayList<Instrument> instrumentsArray = new ArrayList<>();
        instrumentsArray.add( new Instrument(R.string.bass,R.color.bass,25,R.drawable.bass,0f));
        instrumentsArray.add( new Instrument(R.string.banjo,R.color.banjo,10,R.drawable.banjo,0f));
        instrumentsArray.add( new Instrument(R.string.guitar,R.color.guitar,24,R.drawable.guitar,0f));
        instrumentsArray.add( new Instrument(R.string.electric,R.color.electric,40, R.drawable.electric,0f));
         InstrumentsRepo.getInstance().setInstrumentsArray(instrumentsArray);
    }
}
