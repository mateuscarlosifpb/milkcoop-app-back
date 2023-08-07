package com.milkcoop.controllers;

import com.milkcoop.data.model.vo.InventorySaleVO;
import com.milkcoop.services.InventorySaleServices;
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
@RequestMapping("/api/inventory-sale")
public class InventorySaleController {
    @Autowired
    private InventorySaleServices services;

    @PostMapping
    public InventorySaleVO create(@RequestBody InventorySaleVO inventorySale) throws ParseException {
        var inventorySaleVO = services.create(inventorySale);
        inventorySaleVO.add(linkTo(methodOn(InventorySaleController.class).findById(inventorySaleVO.getId())).withSelfRel());
        return inventorySaleVO;
    }

    @GetMapping(value = "/{id}")
    public InventorySaleVO findById(@PathVariable(value = "id") Long id) {
        var inventorySaleVO = services.findById(id);
        inventorySaleVO.add(linkTo(methodOn(InventorySaleController.class).findById(inventorySaleVO.getId())).withSelfRel());
        return inventorySaleVO;
    }

    @PostMapping(path = "/findAll")
    public ResponseEntity<Page<InventorySaleVO>> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                                         @RequestParam(value = "limit", defaultValue = "12") int limit,
                                                         @RequestParam(value = "direction", defaultValue = "asc") String direction,
                                                         @RequestBody InventorySaleVO request) {

        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "dataRegister"));
        Page<InventorySaleVO> inventorySalesVOS = services.find(pageable, request.getDataRegister());
        return new ResponseEntity<>(inventorySalesVOS, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public InventorySaleVO update(@RequestBody InventorySaleVO inventorySale) {
        var inventorySaleVO = services.update(inventorySale);
        inventorySaleVO.add(linkTo(methodOn(InventorySaleController.class).findById(inventorySaleVO.getId())).withSelfRel());
        return inventorySaleVO;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        services.delete(id);
        return ResponseEntity.ok().build();
    }
}
