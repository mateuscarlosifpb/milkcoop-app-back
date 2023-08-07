package com.milkcoop.controllers;


import com.milkcoop.data.model.vo.ProducerVO;
import com.milkcoop.services.impl.ProducerServicesImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/producer")
public class ProducerController {

    @Autowired
    private ProducerServicesImpl services;

    @PostMapping
    public ProducerVO create(@RequestBody ProducerVO producer) throws ParseException {
        var producerVO = services.create(producer);
        producerVO.add(linkTo(methodOn(ProducerController.class).findById(producerVO.getId())).withSelfRel());
        return producerVO;
    }

    @GetMapping(value = "/{id}")
    public ProducerVO findById(@PathVariable(value = "id") Long id) {
        ProducerVO producerVO = services.findById(id);
        producerVO.add(linkTo(methodOn(ProducerController.class).findById(producerVO.getId())).withSelfRel());
        return producerVO;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Operation(summary = "Find all categories")
    @PostMapping(path = "/findAll")
    public ResponseEntity<Page<ProducerVO>> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                                    @RequestParam(value = "limit", defaultValue = "12") int limit,
                                                    @RequestParam(value = "direction", defaultValue = "asc") String direction,
                                                    @RequestBody ProducerVO request) {

        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "fullName"));
        Page<ProducerVO> producerVOS = services.find(pageable, request);
        return new ResponseEntity<>(producerVOS, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ProducerVO update(@RequestBody ProducerVO producer) {
        var producerVO = services.update(producer);
        producerVO.add(linkTo(methodOn(CooperativeController.class).findById(producerVO.getId())).withSelfRel());
        return producerVO;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        services.delete(id);
        return ResponseEntity.ok().build();
    }
}
