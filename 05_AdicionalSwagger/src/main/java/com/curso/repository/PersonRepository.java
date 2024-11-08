package com.curso.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.curso.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{

}
