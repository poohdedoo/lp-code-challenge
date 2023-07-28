package com.littlepay.code.challenge.persistence;

import com.littlepay.code.challenge.Trip;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.List;

public class CSVBasedTripDataPersister implements TripDataPersister {
    private final Path outputDirPath;
    private static final String TRIP_DATA_TARGET_PATH = "trips.csv";
    private static final Logger LOG = LogManager.getLogger(CSVBasedTripDataPersister.class);

    public CSVBasedTripDataPersister(final Path outputDirPath) throws TripDataPersisterInitializationException {
        this.outputDirPath = outputDirPath;
        this.init();
    }

    private void init() throws TripDataPersisterInitializationException {
        if (!Files.exists(outputDirPath)) {
            try {
                Files.createDirectory(outputDirPath);
                LOG.warn("Output directory didn't exist. Therefore, it has been created automatically.");
            } catch (IOException e) {
                throw new TripDataPersisterInitializationException(
                        "Error occurred while initializing the trip data persister", e);
            }
        }
    }

    @Override
    public void persist(List<Trip> trips) throws TripDataPersistenceException {
        if (trips == null || trips.isEmpty()) {
            throw new TripDataPersistenceException("Trip data is null or empty.");
        }

        try (BufferedWriter writer =
                     new BufferedWriter(Files.newBufferedWriter(outputDirPath.resolve(TRIP_DATA_TARGET_PATH)));
             CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT.builder()
                     .setHeader("Started", "Finished", "DurationSecs", "FromStopId", "ToStopId", "ChargeAmount",
                             "CompanyId", "BusID", "PAN", "Status")
                     .build())) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            for (Trip trip: trips) {
                printer.printRecord(sdf.format(trip.getStartTime()), sdf.format(trip.getEndTime()), trip.getDuration(),
                        trip.getOrigin(), trip.getDestination(), String.format("$%.2f", trip.getFee()),
                        trip.getCompanyId(), trip.getBusId(), trip.getPan(), trip.getStatus());
            }
            printer.flush();
        } catch (IOException e) {
            throw new TripDataPersistenceException(
                    "Error occurred while persisting trip info to the target .csv file.", e);
        }
    }
}
