package com.littlepay.code.challenge;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TripProcessorUtilTests {
    @Test
    public void whenTapDataIsNull_throwException() {
        Assertions.assertThrows(TripProcessorException.class, () -> {
           TripProcessorUtil.generateTrips(null);
        });
    }

    @Test
    public void whenTapDataIsEmpty_throwException() {
        Assertions.assertThrows(TripProcessorException.class, () -> {
            TripProcessorUtil.generateTrips(new ArrayList<>(0));
        });
    }

    @Test
    public void whenTapDataIsValid_returnTripData() throws ParseException, TripProcessorException {
        List<Tap> taps = new ArrayList<>();
        taps.add(new Tap(1, new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss").parse("22-01-2023 13:00:00"), Tap.Type.ON, "Stop1", "Company1", "Bus37",
                "5500005555555559"));
        taps.add(new Tap(2, new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss").parse("22-01-2023 13:00:05"), Tap.Type.OFF, "Stop2", "Company1", "Bus37",
                "5500005555555559"));

        List<Trip> trips = TripProcessorUtil.generateTrips(taps);
        assertFalse(trips.isEmpty());
        assertEquals(trips.size(), 1);
    }
}
