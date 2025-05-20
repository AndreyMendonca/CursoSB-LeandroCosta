package com.Erudio.demo.services;

import com.Erudio.demo.entities.Person;
import com.Erudio.demo.exception.BadRequestException;
import com.Erudio.demo.exception.FileStorageException;
import com.Erudio.demo.file.exporter.contract.FileExporter;
import com.Erudio.demo.file.exporter.factory.FileExporterFactory;
import com.Erudio.demo.file.importer.contract.FileImporter;
import com.Erudio.demo.file.importer.factory.FileImporterFactory;
import com.Erudio.demo.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    @Autowired
    private PersonRepository repository;

    @Autowired
    FileImporterFactory importer;

    @Autowired
    FileExporterFactory exporter;

    public Person save(Person person){
        return repository.save(person);
    }
    public Person findById(Long id){
        return repository.findById(id).orElseThrow(()->new RuntimeException());
    }
    public List<Person> findAll(){
        return repository.findAll();
    }
    public Person update(Person person, Long id){
        Person entity = this.findById(id);
        person.setId(entity.getId());
        return repository.save(person);
    }
    public void delete(Long id){
        Person entity = this.findById(id);
        repository.delete(entity);
    }

    public Page<Person> findAllPaginado(Pageable pageable){
        var people = repository.findAll(pageable);
        return people;
    }

    public Page<Person> findAllPaginadoOrdenado(Pageable pageable){
        var people = repository.findAll(pageable);
        return people;
    }

    public List<Person> massCreation(MultipartFile file){

        if(file.isEmpty()) throw new BadRequestException("file is invalid");

        try(InputStream inputStream = file.getInputStream()){
            String filename = Optional.ofNullable(file.getOriginalFilename())
                    .orElseThrow(()-> new BadRequestException("Filename cannot be null"));

            FileImporter importer = this.importer.getImporter(filename);

            List<Person> entities = importer.importFile(inputStream);

            return repository.saveAll(entities);
        } catch (Exception e) {
            throw new FileStorageException("Erro ao processar arquivo");
        }
    }

    public Resource exportPage(String acceptHeader){
        var people = repository.findAll();

        try {
            FileExporter exporter = this.exporter.getExporter(acceptHeader);
            return exporter.exportFile(people);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
