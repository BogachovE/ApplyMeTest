package com.applymetest.Mappers;

import com.applymetest.Models.Instrument;
import com.applymetest.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by bogachov on 12.08.17.
 */

public class InstrumentsResponseMapper {
    static public ArrayList<Instrument> map(HashMap response) {
        ArrayList<Instrument> instrumentsArray = new ArrayList<>();
        instrumentsArray.add( new Instrument(R.string.bass,R.color.bass, ((Double) response.get("bass")).intValue(),R.drawable.bass,0f));
        instrumentsArray.add( new Instrument(R.string.banjo,R.color.banjo,((Double) response.get("banjo")).intValue(),R.drawable.banjo,0f));
        instrumentsArray.add( new Instrument(R.string.guitar,R.color.guitar,((Double) response.get("guitar")).intValue(),R.drawable.guitar,0f));
        instrumentsArray.add( new Instrument(R.string.electric,R.color.electric,((Double) response.get("electric")).intValue(), R.drawable.electric,0f));
        return  instrumentsArray;
    }
}
