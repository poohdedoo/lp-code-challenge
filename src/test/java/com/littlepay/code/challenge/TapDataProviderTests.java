package com.littlepay.code.challenge;

import com.littlepay.code.challenge.load.CSVBasedTapDataLoader;
import com.littlepay.code.challenge.load.TapDataLoader;
import com.littlepay.code.challenge.load.TapDataLoaderException;
import com.littlepay.code.challenge.load.TapDataLoaderFactory;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TapDataProviderTests {
    private static final String TAP_DATA_CSV_FILE_NAME = "taps.csv";

    @BeforeAll
    public static void init() throws IOException {
        Files.deleteIfExists(Paths.get(
                System.getProperty("user.dir"), "input", TAP_DATA_CSV_FILE_NAME));
    }

    @Test
    public void whenInputDoesntExist_dataProviderExceptionThrown() {
        Assertions.assertThrows(TapDataLoaderException.class, () -> {
            TapDataLoader tapDataProvider = new CSVBasedTapDataLoader(Paths.get(
                    System.getProperty("user.dir"), "input", TAP_DATA_CSV_FILE_NAME));
            tapDataProvider.getTaps();
        });
    }

    @Test
    public void whenInputExists_listOfValidTapsReturned() throws TapDataLoaderException, IOException, ParseException {
        Path path = Paths.get(System.getProperty("user.dir"), "input", TAP_DATA_CSV_FILE_NAME);

        try (BufferedWriter writer = new BufferedWriter(Files.newBufferedWriter(path));
             CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT.builder()
                     .setHeader("ID", "DateTimeUTC", "TapType", "StopId", "CompanyId", "BusId", "PAN")
                     .build())) {
            printer.printRecord(
                    "1", "22-01-2023 13:00:00", "ON", "Stop1", "Company1", "Bus37", "5500005555555559");
            printer.printRecord(
                    "2", "22-01-2023 13:05:00", "OFF", "Stop2", "Company1", "Bus37", "5500005555555559");
            printer.printRecord(
                    "3", "22-01-2023 09:20:00", "ON", "Stop3", "Company1", "Bus36", "4111111111111111");
            printer.printRecord(
                    "4", "23-01-2023 08:00:00", "ON", "Stop1", "Company1", "Bus37", "4111111111111111");
            printer.printRecord(
                    "5", "23-01-2023 08:02:00", "OFF", "Stop1", "Company1", "Bus37", "4111111111111111");
            printer.printRecord(
                    "6", "24-01-2023 16:30:00", "OFF", "Stop2", "Company1", "Bus37", "5500005555555559");

            printer.flush();
        }

        TapDataLoader tapDataProvider = TapDataLoaderFactory.getTapDataProvider();
        List<Tap> taps = tapDataProvider.getTaps();
        assertFalse(taps.isEmpty(), "A non-empty array list expected. What was returned instead was " +
                "an empty list.");
        assertEquals(6, taps.size());

        Tap firstTap = taps.get(0);
        assertEquals(1, firstTap.getId());
        assertEquals(new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss").parse("22-01-2023 13:00:00"), firstTap.getDateTime());
        assertEquals(Tap.Type.ON, firstTap.getTapType());
        assertEquals("Stop1", firstTap.getStopId());
        assertEquals("Company1", firstTap.getCompanyId());
        assertEquals("Bus37", firstTap.getBusId());
        assertEquals("5500005555555559", firstTap.getPan());
    }
}
