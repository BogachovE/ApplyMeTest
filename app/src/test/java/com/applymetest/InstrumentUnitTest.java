package com.applymetest;

import android.content.Context;
import android.test.mock.MockContext;

import com.applymetest.Models.Instrument;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by bogachov on 12.08.17.
 */

public class InstrumentUnitTest {
    Instrument instrument;
    @Before
    public void setUp() {
       instrument = new Instrument(
                R.string.bass,
                R.color.bass,
                0,
                R.drawable.bass,
                0f
        );


    }
    @Test
    public void shouldSet() throws Exception {
        instrument.setTitle(R.string.banjo);
        instrument.setColor(R.color.banjo);
        instrument.setLikeCount(3);
        instrument.setImage(R.drawable.banjo);
        instrument.setPercent(3f);

        assertTrue(instrument.getTitle() == R.string.banjo);
        assertTrue(instrument.getColor() == R.color.banjo);
        assertTrue(instrument.getLikeCount() == 3);
        assertTrue(instrument.getImage() == R.drawable.banjo);
        assertTrue(instrument.getPercent() == 3f);
    }

    @Test
    public void shouldAddLike() throws Exception {
        instrument.setLikeCount(3);
        instrument.addLikeCount();
        assertTrue(instrument.getLikeCount() == 4);
    }
}
