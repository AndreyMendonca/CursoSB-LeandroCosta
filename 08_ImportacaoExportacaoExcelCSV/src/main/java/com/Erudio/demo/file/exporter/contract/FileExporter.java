package com.Erudio.demo.file.exporter.contract;

import com.Erudio.demo.entities.Person;
import org.springframework.core.io.Resource;

import java.util.List;

public interface FileExporter {
    Resource exportFile(List<Person> people) throws Exception;
}
