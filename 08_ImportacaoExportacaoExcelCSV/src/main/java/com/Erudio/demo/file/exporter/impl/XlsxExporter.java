package com.Erudio.demo.file.exporter.impl;

import com.Erudio.demo.entities.Person;
import com.Erudio.demo.file.exporter.contract.FileExporter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Component
public class XlsxExporter implements FileExporter {
    @Override
    public Resource exportFile(List<Person> people) throws Exception {
        try(Workbook workbook = new XSSFWorkbook()){
            Sheet sheet = workbook.createSheet("People");
            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID","Nome","Sobrenome","Endereco","Genero","Ativo"};
            for (int i = 0; i < headers.length; i++){
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(createHeaderCellStyle(workbook));
            }
            int rowIndex = 1;
            for(Person person : people){
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(person.getId());
                row.createCell(1).setCellValue(person.getNome());
                row.createCell(2).setCellValue(person.getSobrenome());
                row.createCell(3).setCellValue(person.getEndereco());
                row.createCell(4).setCellValue(person.getEndereco());
                row.createCell(5).setCellValue(person.getAtivo()!= null && person.getAtivo() ? "Yes" : "No");
            }

            for(int i = 0; i< headers.length; i++){
                sheet.autoSizeColumn(i);
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return new ByteArrayResource(outputStream.toByteArray());
        }
    }

    private CellStyle createHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }
}
