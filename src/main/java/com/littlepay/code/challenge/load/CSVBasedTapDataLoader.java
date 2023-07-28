package com.littlepay.code.challenge.load;

import com.littlepay.code.challenge.Tap;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CSVBasedTapDataLoader implements TapDataLoader {
    private final Path sourcePath;

    public CSVBasedTapDataLoader(final Path sourcePath) {
        this.sourcePath = sourcePath;
    }

    @Override
    public List<Tap> getTaps() throws TapDataLoaderException {
        if (!Files.exists(sourcePath)) {
            throw new TapDataLoaderException("The input data file does not exist.");
        }

        List<Tap> taps = new ArrayList<>(0);
        try (
                Reader reader = Files.newBufferedReader(this.sourcePath);
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
