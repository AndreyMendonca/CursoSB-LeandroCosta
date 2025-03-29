package com.Erudio.demo.file.importer.impl;

import com.Erudio.demo.entities.Person;
import com.Erudio.demo.file.importer.contract.FileImporter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CsvImporter implements FileImporter {
    @Override
    public List<Person> importFile(InputStream inputStream) throws Exception {
        CSVFormat format = CSVFormat.Builder.create()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setIgnoreEmptyLines(true)
                .setTrim(true)
                .build();
        Iterable<CSVRecord> records = format.parse(new InputStreamReader(inputStream));
        return parseRecordsToPerson(records);
    }

    private List<Person> parseRecordsToPerson(Iterable<CSVRecord> records) {
        List<Person> people = new ArrayList<>();

        for(CSVRecord record : records){
            Person ent = new Person();
            ent.setNome(record.get("nome"));
            ent.setSobrenome(record.get("sobrenome"));
            ent.setEndereco(record.get("address"));
            ent.setGenero(record.get("genero"));
            ent.setAtivo(true);
            people.add(ent);
        }

        return people;
    }
}
