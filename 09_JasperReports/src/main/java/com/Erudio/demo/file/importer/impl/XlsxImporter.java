package com.Erudio.demo.file.importer.impl;

import com.Erudio.demo.entities.Person;
import com.Erudio.demo.file.importer.contract.FileImporter;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class XlsxImporter implements FileImporter {
    @Override
    public List<Person> importFile(InputStream inputStream) throws Exception {
        try(XSSFWorkbook workbook = new XSSFWorkbook(inputStream)){
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            if(rowIterator.hasNext()){
                rowIterator.next();
            }
            return parseRowsToPersonList(rowIterator);
        }
    }

    private List<Person> parseRowsToPersonList(Iterator<Row> rowIterator) {
        List<Person> people = new ArrayList<>();

        while(rowIterator.hasNext()){
            Row row = rowIterator.next();
            if(isRowValid(row)){
                people.add(perseRowToPerson(row));
            }
        }

        return people;
    }

    private Person perseRowToPerson(Row row) {
        Person ent = new Person();
        ent.setNome(row.getCell(0).getStringCellValue());
        ent.setSobrenome(row.getCell(1).getStringCellValue());
        ent.setEndereco(row.getCell(2).getStringCellValue());
        ent.setGenero(row.getCell(3).getStringCellValue());
        ent.setAtivo(true);
        return ent;
    }

    private static boolean isRowValid(Row row) {
        return row.getCell(0) != null && row.getCell(0).getCellType() != CellType.BLANK;
    }
}
