package com.milkcoop.controllers;

import com.milkcoop.data.model.vo.UserVO;
import com.milkcoop.services.impl.UserServicesImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserServicesImpl services;

    @PostMapping
    public UserVO create(@RequestBody UserVO user) throws ParseException {
        var userVO = services.create(user);
        userVO.add(linkTo(methodOn(UserController.class).findById(userVO.getId())).withSelfRel());
        return userVO;
    }

    @GetMapping(value = "/{id}")
    public UserVO findById(@PathVariable(value = "id") Long id) {
        UserVO userVO = services.findById(id);
        userVO.add(linkTo(methodOn(UserController.class).findById(userVO.getId())).withSelfRel());
        return userVO;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Operation(summary = "Find all categories")
    @GetMapping
    public ResponseEntity<PagedModel<UserVO>> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                                      @RequestParam(value = "limit", defaultValue = "12") int limit,
                                                      @RequestParam(value = "direction", defaultValue = "asc") String direction,
                                                      PagedResourcesAssembler assembler) {

        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "name"));
        Page<UserVO> userVOS = services.find(pageable);
        userVOS.stream()
                .forEach(s -> s.add(linkTo(methodOn(UserController.class).findById(s.getId())).withSelfRel()));
        return new ResponseEntity<>(assembler.toModel(userVOS), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public UserVO update(@RequestBody UserVO user) {
        var userVO = services.update(user);
        userVO.add(linkTo(methodOn(CooperativeController.class).findById(userVO.getId())).withSelfRel());
        return userVO;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        services.delete(id);
        return ResponseEntity.ok().build();
    }
}
