package com.Erudio.demo.controllers;

import com.Erudio.demo.entities.Person;
import com.Erudio.demo.file.exporter.MediaTypes;
import com.Erudio.demo.services.PersonService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonService service;

    @PostMapping
    public Person save(@RequestBody Person person){
        return service.save(person);
    }

    @GetMapping("/{id}")
    public Person findByid(@PathVariable Long id){
        return service.findById(id);
    }

    @GetMapping
    public List<Person> findAll(){
        return service.findAll();
    }

    @PutMapping("/{id}")
    public Person update(@RequestBody Person person, @PathVariable Long id){
        return service.update(person, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

    @GetMapping("/paginada")
    public ResponseEntity<Page<Person>> findAll(
            @RequestParam(value = "page", defaultValue = "0")Integer page,
            @RequestParam(value = "size", defaultValue = "12")Integer size
    ){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(service.findAllPaginado(pageable));
    }

    @GetMapping("/paginadaOrdenado")
    public ResponseEntity<Page<Person>> findAll(
            @RequestParam(value = "page", defaultValue = "0")Integer page,
            @RequestParam(value = "size", defaultValue = "12")Integer size,
            @RequestParam(value = "direction", defaultValue = "asc")String direction
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "nome"));
        return ResponseEntity.ok(service.findAllPaginadoOrdenado(pageable));
    }

    @PostMapping("/massCreation")
    public List<Person> massCreation(@RequestParam("file") MultipartFile file){
        return service.massCreation(file);
    }

    @GetMapping("/exportPage")
    public ResponseEntity<Resource> exportPage(HttpServletRequest request){
        String acceptHeader = request.getHeader(HttpHeaders.ACCEPT);
        Resource file = service.exportPage(acceptHeader);

        Map<String, String> extensionMap = Map.of(
                MediaTypes.APPLICATION_PDF_VALUE, ".pdf",
                MediaTypes.APPLICATION_CSV_VALUE, ".csv",
                MediaTypes.APPLICATION_XLSX_VALUE, ".xlsx"
        );
        var fileExtension = extensionMap.getOrDefault(acceptHeader, "");
        var contentType = acceptHeader != null ? acceptHeader : "application/octet-stream";
        var filename = "peope_exported" + fileExtension;

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + filename+"\""
                ).body(file);
    }

}
