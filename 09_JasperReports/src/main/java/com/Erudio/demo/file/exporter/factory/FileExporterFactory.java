package com.Erudio.demo.file.exporter.factory;

import com.Erudio.demo.exception.BadRequestException;
import com.Erudio.demo.file.exporter.MediaTypes;
import com.Erudio.demo.file.exporter.contract.FileExporter;
import com.Erudio.demo.file.exporter.impl.CsvExporter;
import com.Erudio.demo.file.exporter.impl.PdfExporter;
import com.Erudio.demo.file.exporter.impl.XlsxExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class FileExporterFactory {
    private Logger logger = LoggerFactory.getLogger(FileExporterFactory.class);

    @Autowired
    private ApplicationContext context;

    public FileExporter getExporter(String acceptHeader) throws Exception{
        if(acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_XLSX_VALUE)){
            return context.getBean(XlsxExporter.class);
        } else if (acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_CSV_VALUE)) {
            return context.getBean(CsvExporter.class);
        }    else if (acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_PDF_VALUE)) {
                return context.getBean(PdfExporter.class);
        }else{
            throw new BadRequestException("Formato invalido");
        }
    }
}
