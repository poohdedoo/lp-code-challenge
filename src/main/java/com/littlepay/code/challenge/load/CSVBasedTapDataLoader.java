package com.littlepay.code.challenge.load;

import com.littlepay.code.challenge.Tap;
import com.littlepay.code.challenge.persistence.TripDataPersisterInitializationException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CSVBasedTapDataLoader implements TapDataLoader {
    private final Path inputDirPath;
    private static final String TAP_DATA_SOURCE_PATH = "taps.csv";
    private static final Logger LOG = LogManager.getLogger(CSVBasedTapDataLoader.class);

    public CSVBasedTapDataLoader(final Path inputDirPath) throws TapDataLoaderInitializationException {
        this.inputDirPath = inputDirPath;
        this.init();
    }

    private void init() throws TapDataLoaderInitializationException {
        if (!Files.exists(inputDirPath)) {
            try {
                Files.createDirectory(inputDirPath);
                LOG.warn("Input directory didn't exist. Therefore, it has been created automatically.");
            } catch (IOException e) {
                throw new TapDataLoaderInitializationException(
                        "Error occurred while initializing the tap data loader", e);
            }
        }

        if (!Files.exists(inputDirPath.resolve(TAP_DATA_SOURCE_PATH))) {
            throw new TapDataLoaderInitializationException("The input data file does not exist.");
        }
    }

    @Override
    public List<Tap> getTaps() throws TapDataLoaderException {
        List<Tap> taps = new ArrayList<>(0);
        try (
                Reader reader = Files.newBufferedReader(inputDirPath.resolve(TAP_DATA_SOURCE_PATH));
                CSVParser parser = new CSVParser(reader,
                        CSVFormat.DEFAULT.builder()
                                .setHeader("ID", "DateTimeUTC", "TapType", "StopId", "CompanyId", "BusId", "PAN")
                                .setTrim(true)
                                .setSkipHeaderRecord(true)
                                .build())
        ) {
            for(CSVRecord record: parser) {
                Tap tap = new Tap();
                tap.setId(Integer.parseInt(record.get("ID")));
                tap.setDateTime(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(record.get("DateTimeUTC")));
                tap.setTapType(Tap.Type.valueOf(record.get("TapType")));
                tap.setStopId(record.get("StopId"));
                tap.setCompanyId(record.get("CompanyId"));
                tap.setBusId(record.get("BusId"));
                tap.setPan(record.get("PAN"));
                taps.add(tap);
            }
        } catch (IOException | ParseException e) {
            throw new TapDataLoaderException("Error occurred while parsing the .csv file.", e);
        }

        return taps;
    }
}
