package com.Erudio.demo.file.importer.contract;

import com.Erudio.demo.entities.Person;

import java.io.InputStream;
import java.util.List;

public interface FileImporter {
    List<Person> importFile(InputStream inputStream) throws Exception;
}
