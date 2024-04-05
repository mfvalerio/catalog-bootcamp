package com.devsuperior.catalog.services;

import com.devsuperior.catalog.dto.CategoryDTO;
import com.devsuperior.catalog.entities.CategoryEntity;
import com.devsuperior.catalog.repositories.CategoryRepository;
import com.devsuperior.catalog.services.exceptions.DataBaseException;
import com.devsuperior.catalog.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAll(PageRequest pageRequest) {
        Page<CategoryEntity> list = repository.findAll(pageRequest);

        return list.map(CategoryDTO::new);
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {

        Optional<CategoryEntity> obj = repository.findById(id);
        CategoryEntity category = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new CategoryDTO(category);
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(categoryDTO.getName());
        categoryEntity = repository.save(categoryEntity);
        return new CategoryDTO(categoryEntity);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO categoryDTO) {
        try {
            CategoryEntity categoryEntity = repository.getReferenceById(id);
            categoryEntity.setName(categoryDTO.getName());
            categoryEntity = repository.save(categoryEntity);
            return new CategoryDTO(categoryEntity);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found");
        }
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found");
        }
        catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Integrity violation");
        }
    }
}
