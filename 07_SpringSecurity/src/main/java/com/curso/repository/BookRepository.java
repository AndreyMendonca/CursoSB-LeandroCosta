package com.curso.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.curso.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>{

}
