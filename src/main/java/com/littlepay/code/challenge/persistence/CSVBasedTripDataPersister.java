package com.littlepay.code.challenge.persistence;

import com.littlepay.code.challenge.Trip;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.List;

public class CSVBasedTripDataPersister implements TripDataPersister {
    private final Path targetPath;

    public CSVBasedTripDataPersister(final Path sourcePath) {
        this.targetPath = sourcePath;
    }

    @Override
    public void persist(List<Trip> trips) throws TapDataPersistenceException {
        if (trips == null || trips.isEmpty()) {
            throw new TapDataPersistenceException("Trip data is null or empty.");
        }

        try (BufferedWriter writer = new BufferedWriter(Files.newBufferedWriter(this.targetPath));
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
            throw new TapDataPersistenceException(
                    "Error occurred while persisting trip info to the target .csv file.", e);
        }
    }
}
