package com.curso.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curso.data.vo.v1.PersonVO;
import com.curso.services.PersonService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/person")
@Tag(name="People", description = "Endpoints para class pessoas")
public class PersonController {

	@Autowired
	private PersonService service;
	
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping
	@Operation(
		summary = "Encontrar Pessoas", 
		description = "Encontrar pessoas",
		tags = {"People"},
		responses = {
				@ApiResponse(
						description = "Sucesss", 
						responseCode = "200",
						content = {
								@Content(
										mediaType = "application/json",
										array = @ArraySchema(schema = @Schema(implementation = PersonVO.class))
								)
						}
				),
				@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
				@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
				@ApiResponse(description = "NotFound", responseCode = "404", content = @Content),
				@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			}
	)
	public List<PersonVO> findAll(){
		return service.findAll();
	}
	
	
	@CrossOrigin(origins = {"http://localhost:8080", "http://google.com.br"})
	@GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(
		summary = "Encontrar uma pessoa", 
		description = "Encontrar uma pessoa",
		tags = {"People"},
		responses = {
				@ApiResponse(
						description = "Sucesss", 
						responseCode = "400", 
						content = @Content(
								schema = @Schema(implementation = PersonVO.class)
						)
				),
				@ApiResponse(description = "No Content", responseCode = "404", content = @Content),
				@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
				@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
				@ApiResponse(description = "NotFound", responseCode = "404", content = @Content),
				@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
		}
	
	)
	public PersonVO findById(@PathVariable Long id) {
		return service.findById(id);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(
			summary = "Criar uma pessoa", 
			description = "Criar uma pessoa",
			tags = {"People"},
			responses = {
					@ApiResponse(
							description = "Sucesss", 
							responseCode = "400", 
							content = @Content(
									schema = @Schema(implementation = PersonVO.class)
							)
					),
					@ApiResponse(description = "No Content", responseCode = "404", content = @Content),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "NotFound", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			}
		
		)
	public PersonVO save(@RequestBody PersonVO person) {
		return service.create(person);
	}
	
	@PutMapping("/{id}")
	@Operation(
			summary = "Atualizar uma pessoa", 
			description = "Atualizar uma pessoa",
			tags = {"People"},
			responses = {
					@ApiResponse(
							description = "Sucesss", 
							responseCode = "400", 
							content = @Content(
									schema = @Schema(implementation = PersonVO.class)
							)
					),
					@ApiResponse(description = "No Content", responseCode = "404", content = @Content),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "NotFound", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			}
		
		)
	public PersonVO update(@PathVariable Long id, @RequestBody PersonVO person) {
		return service.update(person, id);
	}
	
	@DeleteMapping("/{id}")
	@Operation(
			summary = "Deletar uma pessoa", 
			description = "Deletar uma pessoa",
			tags = {"People"},
			responses = {
					@ApiResponse(description = "No Content", responseCode = "201", content = @Content),
					@ApiResponse(description = "No Content", responseCode = "404", content = @Content),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "NotFound", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			}
		
		)
	public ResponseEntity<?> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
