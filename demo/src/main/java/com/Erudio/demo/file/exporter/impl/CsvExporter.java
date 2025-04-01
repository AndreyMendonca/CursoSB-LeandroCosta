package com.Erudio.demo.file.exporter.impl;

import com.Erudio.demo.entities.Person;
import com.Erudio.demo.file.exporter.contract.FileExporter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class CsvExporter implements FileExporter {
    @Override
    public Resource exportFile(List<Person> people) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);

        CSVFormat csvFormat = CSVFormat.Builder.create()
                .setHeader("ID","Nome","Sobrenome","Endereco","Genero","Ativo")
                .setSkipHeaderRecord(false)
                .build();

        try(CSVPrinter csvPrinter = new CSVPrinter(writer, csvFormat)){
            for(Person person : people){
                csvPrinter.printRecord(
                        person.getId(),
                        person.getNome(),
                        person.getSobrenome(),
                        person.getEndereco(),
                        person.getGenero(),
                        person.getAtivo()
                );
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new ByteArrayResource(outputStream.toByteArray());
    }
}
