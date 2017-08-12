package Singletons;

import android.content.Context;

import com.applymetest.AddChoose;
import com.applymetest.InstrumentCreator;
import com.applymetest.InstrumentsResource;
import com.applymetest.Models.Instrument;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

import java.util.ArrayList;


public class InstrumentsRepo {
    private Deferred<ArrayList<Instrument>, String, String> getDeferred = new DeferredObject<>();

    private Promise<ArrayList<Instrument>, String, String> getPromise() {
        return getDeferred.promise();
    }

    private Deferred<String, String, String> postDeferred = new DeferredObject<>();

    private Promise<String, String, String> postPromise() {
        return postDeferred.promise();
    }

    private ArrayList<Instrument> instrumentsArray = new ArrayList<>();
    private InstrumentCreator createType;
    private AddChoose addChooseType;
    private static final InstrumentsRepo ourInstance = new InstrumentsRepo();


    public static InstrumentsRepo getInstance() {
        return ourInstance;
    }

    private InstrumentsRepo() {
    }

    public ArrayList<Instrument> getInstrumentsArray() {
        return instrumentsArray;
    }

    public void setInstrumentsArray(ArrayList<Instrument> instrumentsArray) {
        this.instrumentsArray = instrumentsArray;
    }

    public Promise<ArrayList<Instrument>, String, String> createInstruments(Context context) {
        createType.createInstruments(context).then((res) -> {
            getDeferred.resolve(res);
        });
        return getPromise();
    }

    public Promise<String, String, String> addChoose(Context context, Integer position) {
        InstrumentsRepo.getInstance().getInstrumentsArray().get(position).addLikeCount();
        String title = context.getString(getInstrumentsArray().get(position).getTitle());
        if (addChooseType != null) {
            addChooseType.addChoose(context, title).then((res) -> {
                postDeferred.resolve(res);
            });
        } else {
            postDeferred.resolve("Added by local type");
        }
        return postPromise();
    }


    public void setStrategy(InstrumentCreator createType) {
        this.createType = createType;
        if (InstrumentsResource.class.isInstance(createType)) {
            this.addChooseType = new InstrumentsResource();
        } else {
            addChooseType = null;
        }
        updateDeferred();
    }

    public InstrumentCreator getStrategy(){
        return this.createType;
    }

    public void updateDeferred() {
        if (addChooseType != null) {
            this.addChooseType.updateDeferred();
        }
        getDeferred = new DeferredObject<>();
        postDeferred = new DeferredObject<>();
    }


}
