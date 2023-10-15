package com.devsuperior.catalog.controller.service;

import com.devsuperior.catalog.domain.CategoryEntity;
import com.devsuperior.catalog.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public List<CategoryEntity> findAll() {
        return repository.findAll();
    }
}
