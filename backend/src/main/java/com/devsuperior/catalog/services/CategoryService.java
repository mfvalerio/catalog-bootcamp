package com.devsuperior.catalog.service;

import com.devsuperior.catalog.entities.CategoryEntity;
import com.devsuperior.catalog.dto.CategoryDTO;
import com.devsuperior.catalog.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<CategoryEntity> list = repository.findAll();

//        return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
        return list.stream().map(CategoryDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {

        Optional<CategoryEntity> obj = repository.findById(id);
        CategoryEntity category = obj.get();
        return new CategoryDTO(category);
    }
}
