package com.applymetest;

import android.content.Context;

import com.applymetest.Models.Instrument;

import org.jdeferred.Promise;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by bogachov on 08.08.17.
 */

public interface InstrumentCreator {
     Promise<ArrayList<Instrument>, String, String> createInstruments(Context context);
}
