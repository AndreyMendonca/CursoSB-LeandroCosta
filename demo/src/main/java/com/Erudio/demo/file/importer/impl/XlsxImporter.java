package com.Erudio.demo.file.importer.impl;

import com.Erudio.demo.entities.Person;
import com.Erudio.demo.file.importer.contract.FileImporter;

import java.io.InputStream;
import java.util.List;

public class XlsxImporter implements FileImporter {
    @Override
    public List<Person> importFile(InputStream inputStream) throws Exception {
        return List.of();
    }
}
