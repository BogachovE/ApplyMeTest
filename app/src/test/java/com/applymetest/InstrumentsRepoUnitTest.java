package com.applymetest;

import android.content.Context;
import android.os.Build;
import android.test.mock.MockContext;

import com.applymetest.Models.Instrument;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;
import java.util.ArrayList;

import Singletons.InstrumentsRepo;
//resourceDir = "some/build/path/res"
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class InstrumentsRepoUnitTest {

    @Before
    public void setup() {
        Robolectric.buildActivity(ChooseActivity.class).get();

    }
    @Test
    public void shouldSetInstrumentsArray() throws Exception {
        ArrayList<Instrument> instrumentsArray = new ArrayList<>();
        instrumentsArray.add( new Instrument(R.string.bass,R.color.bass,1,R.drawable.bass,0f));
        instrumentsArray.add( new Instrument(R.string.banjo,R.color.banjo,2,R.drawable.banjo,0f));

        InstrumentsRepo.getInstance().setInstrumentsArray(instrumentsArray);

        assertTrue(InstrumentsRepo.getInstance().getInstrumentsArray().get(0).getLikeCount() == 1);
        assertTrue(InstrumentsRepo.getInstance().getInstrumentsArray().get(1).getLikeCount() == 2);
    }

    @Test
    public void shouldSetStrategy() throws Exception {
        InstrumentsRepo.getInstance().setStrategy( new InstrumentsLocalCreator());
        assertTrue(InstrumentsLocalCreator.class.isInstance(InstrumentsRepo.getInstance().getStrategy()));

        InstrumentsRepo.getInstance().setStrategy( new InstrumentsResource());
        assertTrue(InstrumentsResource.class.isInstance(InstrumentsRepo.getInstance().getStrategy()));
    }

    @Test
    public void shouldCreateInstrumentsLocal() throws Exception {
        Context context = new MockContext();
        InstrumentsRepo.getInstance().setStrategy( new InstrumentsLocalCreator());
        InstrumentsRepo.getInstance().createInstruments(context).then((res) -> {
            assertTrue(res.get(0).getLikeCount() == 1);
        });
    }

    @Test
    public void shouldCreateInstrumentsAPI() throws Exception {
        final Context context = RuntimeEnvironment.application;
        InstrumentsRepo.getInstance().setStrategy( new InstrumentsResource());
        InstrumentsRepo.getInstance().createInstruments(context).then((res) -> {
            assertTrue(res.get(0).getLikeCount() == 1);
        });
    }

}
