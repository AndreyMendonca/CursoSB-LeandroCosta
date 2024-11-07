package com.curso.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.curso.controller.PersonController;
import com.curso.data.vo.v1.PersonVO;
import com.curso.exception.ResourceNotFoundException;
import com.curso.mapper.DozerMapper;
import com.curso.model.Person;
import com.curso.repository.PersonRepository;

@Service
public class PersonService {
	
	@Autowired
	private PersonRepository repository;
	
	public List<PersonVO> findAll(){
		List<PersonVO> vos = DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
		
		vos.
			stream()
			.forEach(x -> x.add(linkTo(methodOn(PersonController.class).findById(x.getKey())).withSelfRel()));
		return vos;
	}
	
	public PersonVO findById(Long id) {
		Person entity =  repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("PersonVO not found"));	
		PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return vo;
	}
	
	public PersonVO create(PersonVO person) {
		Person entity =  DozerMapper.parseObject(person, Person.class);
		PersonVO vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public PersonVO update(PersonVO personVO, Long id) {
		
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("PersonVO not found"));
		
		personVO.setKey(entity.getId());
		
		PersonVO vo = DozerMapper
						.parseObject(repository.save(DozerMapper.parseObject(personVO, Person.class)), PersonVO.class);
		
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
		
	}
	
	public void delete(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("PersonVO not found"));
		
		repository.delete(entity);
	}
}
