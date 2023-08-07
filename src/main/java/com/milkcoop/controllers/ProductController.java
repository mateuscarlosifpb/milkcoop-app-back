package com.milkcoop.controllers;


import com.milkcoop.data.model.vo.ProductVO;
import com.milkcoop.services.impl.ProductServicesImpl;
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
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductServicesImpl services;

    @PostMapping
    public ProductVO create(@RequestBody ProductVO product) throws ParseException {
        var productVO = services.create(product);
        productVO.add(linkTo(methodOn(ProducerController.class).findById(productVO.getId())).withSelfRel());
        return productVO;
    }

    @GetMapping(value = "/{id}")
    public ProductVO findById(@PathVariable(value = "id") Long id) {
        ProductVO productVO = services.findById(id);
        productVO.add(linkTo(methodOn(ProducerController.class).findById(productVO.getId())).withSelfRel());
        return productVO;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Operation(summary = "Find all categories")
    @PostMapping(path = "/findAll")
    public ResponseEntity<Page<ProductVO>> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                                   @RequestParam(value = "limit", defaultValue = "12") int limit,
                                                   @RequestParam(value = "direction", defaultValue = "asc") String direction,
                                                   @RequestBody ProductVO request
    ) {

        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "id"));
        Page<ProductVO> productVOS = services.find(pageable, request);
        return new ResponseEntity<>(productVOS, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ProductVO update(@RequestBody ProductVO product) {
        var productVO = services.update(product);
        productVO.add(linkTo(methodOn(CooperativeController.class).findById(productVO.getId())).withSelfRel());
        return productVO;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        services.delete(id);
        return ResponseEntity.ok().build();
    }
}
