package com.littlepay.code.challenge;

import com.littlepay.code.challenge.persistence.TapDataPersistenceException;
import com.littlepay.code.challenge.persistence.TripDataPersister;
import com.littlepay.code.challenge.persistence.TripDataPersisterFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TripDataPersisterTests {
    private static final String TRIP_DATA_CSV_FILE_NAME = "trips.csv";

//    @BeforeAll
//    public static void init() throws IOException {
//        Files.deleteIfExists(Paths.get(
//                System.getProperty("user.dir"), "output", TRIP_DATA_CSV_FILE_NAME));
//    }

    @Test
    public void whenTripInfoIsNull_throwException() {
        TapDataPersistenceException thrown = Assertions.assertThrows(TapDataPersistenceException.class, () -> {
            TripDataPersister persister = TripDataPersisterFactory.getTripDataPersister();
            persister.persist(null);
        });
        assertEquals("Trip data is null or empty.", thrown.getMessage());
    }

    @Test
    public void whenTripInfoIsEmpty_throwException() {
        TapDataPersistenceException thrown = Assertions.assertThrows(TapDataPersistenceException.class, () -> {
            TripDataPersister persister = TripDataPersisterFactory.getTripDataPersister();
            persister.persist(new ArrayList<>(0));
        });
        assertEquals("Trip data is null or empty.", thrown.getMessage());
    }

    @Test
    public void whenTripInfoIsValid_writeToCsv() throws TapDataPersistenceException, ParseException {
        List<Trip> trips = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        trips.add(new Trip(
                sdf.parse("22-01-2023 13:00:00"), sdf.parse("22-01-2023 13:00:05"),
                "Stop1", "Stop2", 3.25, "Company1",
                "Bus37", "5500005555555559", Trip.Status.COMPLETED));

        TripDataPersister persister = TripDataPersisterFactory.getTripDataPersister();
        persister.persist(trips);
    }
}
