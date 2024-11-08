package com.curso.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		Book book = repository
						.findById(id)
						.orElseThrow(() -> new ResourceNotFoundException("Book not found"));
		BookVO vo = DozerMapper.parseObject(book, BookVO.class);
		return vo;
	}
	
	public List<BookVO> findAll(){
		List<Book> books = repository.findAll();
		List<BookVO> vos = DozerMapper.parseListObjects(books, BookVO.class);
		return vos;
	}
	
	public BookVO create(BookVO vo) {
		Book book = DozerMapper.parseObject(vo, Book.class);
		book = repository.save(book);
		vo = DozerMapper.parseObject(book, BookVO.class);
		return vo;
	}

}
