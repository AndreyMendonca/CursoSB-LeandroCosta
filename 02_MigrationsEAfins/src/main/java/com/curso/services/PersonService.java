package com.curso.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		return DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
	}
	
	public PersonVO findById(Long id) {
		Person entity =  repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("PersonVO not found"));	
		return DozerMapper.parseObject(entity, PersonVO.class);
	}
	
	public PersonVO create(PersonVO person) {
		Person entity =  DozerMapper.parseObject(person, Person.class);
		PersonVO vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		return vo;
	}
	
	public PersonVO update(PersonVO personVO, Long id) {
		
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("PersonVO not found"));
		
		personVO.setId(entity.getId());
		
		PersonVO vo = DozerMapper
						.parseObject(repository.save(DozerMapper.parseObject(personVO, Person.class)), PersonVO.class);
		return vo;
		
	}
	
	public void delete(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("PersonVO not found"));
		
		repository.delete(entity);
	}
}
