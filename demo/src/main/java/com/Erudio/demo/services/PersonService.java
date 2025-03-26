package com.Erudio.demo.services;

import com.Erudio.demo.entities.Person;
import com.Erudio.demo.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    @Autowired
    private PersonRepository repository;

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
}
