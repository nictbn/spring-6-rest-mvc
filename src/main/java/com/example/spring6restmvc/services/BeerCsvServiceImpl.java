package com.example.spring6restmvc.services;

import com.example.spring6restmvc.model.BeerCSVRecord;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Component
public class BeerCsvServiceImpl implements BeerCsvService {
    @Override
    public List<BeerCSVRecord> convertCSV(File file) {
        try {
            return new CsvToBeanBuilder<BeerCSVRecord>(new FileReader(file))
                    .withType(BeerCSVRecord.class)
                    .build().parse();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
