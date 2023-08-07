package com.milkcoop.controllers;


import com.milkcoop.data.model.vo.PayrollProducerVO;
import com.milkcoop.data.model.vo.PayrollVO;
import com.milkcoop.services.PayrollServices;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/payroll")
public class PayrollController {
    @Autowired
    private PayrollServices services;

    @PostMapping
    public PayrollVO create(@RequestBody PayrollVO payroll) throws ParseException {
        var payrollVO = services.create(payroll);
        payrollVO.add(linkTo(methodOn(PayrollController.class).findById(payrollVO.getId())).withSelfRel());
        return payrollVO;
    }

    @GetMapping(value = "/{id}")
    public PayrollVO findById(@PathVariable(value = "id") Long id) {
        PayrollVO payrollVO = services.findById(id);
        payrollVO.add(linkTo(methodOn(PayrollController.class).findById(payrollVO.getId())).withSelfRel());
        return payrollVO;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Operation(summary = "Find all categories")
    @PostMapping(path = "/findAll")
    public ResponseEntity<Page<PayrollVO>> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                                   @RequestParam(value = "limit", defaultValue = "12") int limit,
                                                   @RequestParam(value = "direction", defaultValue = "asc") String direction,
                                                   @RequestBody PayrollVO request) {

        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "amount"));
        Page<PayrollVO> producerVOS = services.find(pageable, request);
        return new ResponseEntity<>(producerVOS, HttpStatus.OK);
    }
    @PutMapping(value = "/confirmPayment/{id}")
    public ResponseEntity<?> confirmPayment(@PathVariable(value = "id") Long id) {
        services.confirmPayment(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{id}")
    public PayrollVO update(@RequestBody PayrollVO payroll) {
        var payrollVO = services.update(payroll);
        payrollVO.add(linkTo(methodOn(PayrollController.class).findById(payrollVO.getId())).withSelfRel());
        return payrollVO;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        services.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/generatePayroll")
    public ResponseEntity<PayrollVO> generatePayrollForPendingDeliveries(@RequestBody boolean persist) throws ParseException {
        var payrollVO = services.generatePayrollForPendingDeliveries(persist);
        return new ResponseEntity<>(payrollVO, HttpStatus.OK);
    }
    @PostMapping(path = "/generatePayrollPageable")
    public ResponseEntity<Object> generatePayrollPageable(@RequestParam(value = "page", defaultValue = "0") int page,
                                                             @RequestParam(value = "limit", defaultValue = "2") int limit,
                                                             @RequestParam(value = "direction", defaultValue = "asc") String direction,
                                                             @Valid @RequestBody List<PayrollProducerVO> request) {
        try {
            var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
            Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "amount"));
            Page<PayrollProducerVO> pagina = new PageImpl<>(request, pageable, request.size());
            return new ResponseEntity<Object>(pagina, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro não tratado: " + e.getMessage());
        }
    }
}
