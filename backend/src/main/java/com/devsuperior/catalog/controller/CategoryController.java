package com.devsuperior.catalog.controller;

import com.devsuperior.catalog.controller.service.CategoryService;
import com.devsuperior.catalog.domain.CategoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping
    public ResponseEntity<List<CategoryEntity>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }
}
