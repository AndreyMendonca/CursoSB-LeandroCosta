package com.curso.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curso.controller.BookController;
import com.curso.data.vo.v1.BookVO;
import com.curso.exception.ResourceNotFoundException;
import com.curso.mapper.DozerMapper;
import com.curso.model.Book;
import com.curso.repository.BookRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository repository;

	public BookVO findById(Long id) {
		Book book = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
		BookVO vo = DozerMapper.parseObject(book, BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}

	public List<BookVO> findAll() {
		List<Book> books = repository.findAll();
		List<BookVO> vos = DozerMapper.parseListObjects(books, BookVO.class);
		
		vos
			.stream()
				.forEach(
						x -> x.add(
							linkTo(methodOn(BookController.class).findById(x.getKey())).withSelfRel()));
		
		return vos;
	}

	public Book create(Book vo) {
		//Book book = DozerMapper.parseObject(vo, Book.class);
		Book book = repository.save(vo);
		BookVO voo = DozerMapper.parseObject(book, BookVO.class);
		voo.add(linkTo(methodOn(BookController.class).findById(voo.getKey())).withSelfRel());
		return book;
	}

	public BookVO update(BookVO vo, Long id) {
		Book book = repository
					.findById(id)
						.orElseThrow(
								() -> new ResourceNotFoundException("Book not found")
						);
		
		vo.setKey(book.getId());
		book = repository.save(DozerMapper.parseObject(vo, Book.class));
		vo = DozerMapper.parseObject(book, BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public void delete(Long id) {
		repository
		.findById(id)
			.orElseThrow(
					() -> new ResourceNotFoundException("Book not found"));
		
		repository.deleteById(id);
	}

}
