package Singletones;

import com.applymetest.InstrumentCreator;
import com.applymetest.InstrumentsLocalCreator;
import com.applymetest.Models.Instrument;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by bogachov on 08.08.17.
 */

public class InstrumentsRepo {
    private ArrayList<Instrument> instrumentsArray = new ArrayList<>();
    private InstrumentCreator createType;
    private static final InstrumentsRepo ourInstance = new InstrumentsRepo();

    public static InstrumentsRepo getInstance() {
        return ourInstance;
    }

    private InstrumentsRepo() {
        createType = new InstrumentsLocalCreator();
    }

    public ArrayList<Instrument> getInstrumentsArray() {
        return instrumentsArray;
    }

    public void setInstrumentsArray(ArrayList<Instrument> instrumentsArray) {
        this.instrumentsArray = instrumentsArray;
    }

    public void createInstruments() throws IOException {
        createType.createInstruments();
    }

    public void setCreateType(InstrumentCreator createType) {
        this.createType = createType;
    }

    public InstrumentCreator getCreateType() {
        return createType;
    }
}
