package com.milkcoop.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.milkcoop.data.model.vo.CooperativeVO;
import com.milkcoop.services.impl.CooperativeServicesImpl;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/cooperative")
public class CooperativeController {

	@Autowired
	private CooperativeServicesImpl services;

	@PostMapping
	public CooperativeVO create(@RequestBody CooperativeVO cooperative) throws ParseException {
		var cooperativeVO = services.create(cooperative);
		cooperativeVO.add(linkTo(methodOn(CooperativeController.class).findById(cooperativeVO.getKey())).withSelfRel());
		return cooperativeVO;
	}

	@GetMapping(value = "/{id}")
	public CooperativeVO findById(@PathVariable(value = "id") Long id) {
		CooperativeVO cooperativeVO = services.findById(id);
		cooperativeVO.add(linkTo(methodOn(CooperativeController.class).findById(cooperativeVO.getKey())).withSelfRel());
		return cooperativeVO;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Operation(summary = "Find all categories")
	@GetMapping
	public ResponseEntity<PagedModel<CooperativeVO>> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "12") int limit,
			@RequestParam(value = "direction", defaultValue = "asc") String direction,
			PagedResourcesAssembler assembler) {

		var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "name"));
		Page<CooperativeVO> cooperativeVO = services.find(pageable);
		cooperativeVO.stream()
				.forEach(s -> s.add(linkTo(methodOn(CooperativeController.class).findById(s.getKey())).withSelfRel()));
		return new ResponseEntity<>(assembler.toModel(cooperativeVO), HttpStatus.OK);
	}

	@PutMapping(value = "/{id}")
	public CooperativeVO update(@RequestBody CooperativeVO cooperative) {
		var cooperativeVO = services.update(cooperative);
		cooperativeVO.add(linkTo(methodOn(CooperativeController.class).findById(cooperative.getKey())).withSelfRel());
		return cooperativeVO;
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		services.delete(id);
		return ResponseEntity.ok().build();
	}
}
