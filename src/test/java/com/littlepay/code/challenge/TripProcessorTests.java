package com.littlepay.code.challenge;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class TripProcessorTests {
    private static final String TRIP_DATA_CSV_FILE_NAME = "trips.csv";

    @BeforeAll
    public static void init() throws IOException {
        Files.deleteIfExists(Paths.get(
                System.getProperty("user.dir"), "output", TRIP_DATA_CSV_FILE_NAME));
    }

    @Test
    public void whenInvoked_tripInfoCSVisGenerated() {
        TripProcessor tripProcessor = new TripProcessor();
        try {
            tripProcessor.processTrips();
        } catch (TripProcessorException e) {
            fail("Error occurred while processing trips");
        }

        Path outputPath = Paths.get(System.getProperty("user.dir"), "output", TRIP_DATA_CSV_FILE_NAME);
        assertTrue(Files.exists(outputPath), "Trip data .csv file has not been created.");
    }
}
