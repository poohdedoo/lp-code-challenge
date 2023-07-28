package com.littlepay.code.challenge;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public final class TripProcessorUtil {
    public static List<Trip> generateTrips(List<Tap> taps) throws TripProcessorException {
        if (taps == null || taps.isEmpty()) {
            throw new TripProcessorException("Tap data is null or empty.");
        }

        //FIXME: Fix the following code block with proper trip info generation logic.
        List<Trip> trips = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            trips.add(new Trip(
                    sdf.parse("22-01-2023 13:00:00"), sdf.parse("22-01-2023 13:00:05"),
                    "Stop1", "Stop2", 3.25, "Company1",
                    "Bus37", "5500005555555559", Trip.Status.COMPLETED));
        } catch (ParseException e) {
            throw new TripProcessorException("Error occurred while processing trip data.", e);
        }
        return trips;
    }
}
