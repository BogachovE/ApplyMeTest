package com.applymetest;

import com.applymetest.Mappers.InstrumentsResponseMapper;
import com.applymetest.Models.Instrument;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;


public class InstrumentsResponseMapperUnitTest {
    @Test
    public void shouldMapped() throws Exception {
        HashMap map = new HashMap();
        map.put("bass", 25.00);
        map.put("banjo", 10.00);
        map.put("guitar", 25.00);
        map.put("electric", 40.00);
        map.put("status", "ok");

        ArrayList<Instrument>  result = InstrumentsResponseMapper.map(map);

        assertEquals(result.get(0).getLikeCount().intValue(), 25);
        assertEquals(result.get(1).getLikeCount().intValue(), 10);
        assertEquals(result.get(2).getLikeCount().intValue(), 25);
        assertEquals(result.get(3).getLikeCount().intValue(), 40);
    }
}