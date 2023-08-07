package com.milkcoop.controllers;

import com.milkcoop.data.model.vo.ProducerDeliveryVO;
import com.milkcoop.services.impl.ProducerDeliveryServicesImpl;
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
@RequestMapping("/api/producer-delivery")
public class ProducerDeliveryController {
    @Autowired
    ProducerDeliveryServicesImpl services;

    @PostMapping
    public ProducerDeliveryVO create(@RequestBody ProducerDeliveryVO producerDelivery) throws ParseException {
        var producerDeliveryVO = services.create(producerDelivery);
        producerDeliveryVO.add(linkTo(methodOn(ProducerDeliveryController.class).findById(producerDeliveryVO.getId())).withSelfRel());
        return producerDeliveryVO;
    }

    @GetMapping(value = "/{id}")
    public ProducerDeliveryVO findById(@PathVariable(value = "id") Long id) {
        var producerDeliveryVO = services.findById(id);
        producerDeliveryVO.add(linkTo(methodOn(ProducerDeliveryController.class).findById(producerDeliveryVO.getId())).withSelfRel());
        return producerDeliveryVO;
    }

    @GetMapping(value = "/report")
    public ResponseEntity<Object> report() {
        var report = services.organizeInformation();
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    @PostMapping(value = "/dashBoard")
    public ResponseEntity<Object> dashboardProducer(@RequestBody ProducerDeliveryVO request) {
        var dashboardProducer = services.dashboardProducer(request.getProducer().getId());
        return new ResponseEntity<>(dashboardProducer, HttpStatus.OK);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Operation(summary = "Find all categories")
    @PostMapping(path = "/findAll")
    public ResponseEntity<Page<ProducerDeliveryVO>> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                                            @RequestParam(value = "limit", defaultValue = "12") int limit,
                                                            @RequestParam(value = "direction", defaultValue = "asc") String direction,
                                                            @RequestBody ProducerDeliveryVO request) {

        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "dataRegister"));
        Page<ProducerDeliveryVO> producerDeliveryVOS = services.find(pageable, request.getProducer().getId());
        return new ResponseEntity<>(producerDeliveryVOS, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ProducerDeliveryVO update(@RequestBody ProducerDeliveryVO producerDelivery) {
        var producerDeliveryVO = services.update(producerDelivery);
        producerDeliveryVO.add(linkTo(methodOn(ProducerDeliveryController.class).findById(producerDeliveryVO.getId())).withSelfRel());
        return producerDeliveryVO;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        services.delete(id);
        return ResponseEntity.ok().build();
    }
}
