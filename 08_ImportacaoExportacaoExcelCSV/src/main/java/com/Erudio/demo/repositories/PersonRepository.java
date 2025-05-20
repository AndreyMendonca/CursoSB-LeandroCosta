package com.Erudio.demo.repositories;

import com.Erudio.demo.entities.Person;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<Person, Long> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Person p SET p.ativo = false WHERE p.id =:id")
    void disabilitarPerson(@Param("id") Long id);
}
