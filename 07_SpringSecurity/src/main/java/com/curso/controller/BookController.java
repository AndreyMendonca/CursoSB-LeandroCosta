package com.curso.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curso.data.vo.v1.BookVO;
import com.curso.model.Book;
import com.curso.services.BookService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/books")
public class BookController {
	
	@Autowired
	private BookService service;
	
	@GetMapping("/{id}")
	public BookVO findById(@PathVariable Long id) {
		return service.findById(id);
	}
	
	@GetMapping
	public List<BookVO> findAll() {
		return service.findAll();
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public Book create(@RequestBody Book vo) {
		return service.create(vo);
	}
	
	@PutMapping("/{id}")
	public BookVO update(@RequestBody BookVO vo, @PathVariable Long id) {
		return service.update(vo,id);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
