package com.devsuperior.catalog.services;

import com.devsuperior.catalog.dto.ProductDTO;
import com.devsuperior.catalog.entities.ProductEntity;
import com.devsuperior.catalog.repositories.ProductRepository;
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
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(PageRequest pageRequest) {
        Page<ProductEntity> list = repository.findAll(pageRequest);

        return list.map(ProductDTO::new);
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<ProductEntity> obj = repository.findById(id);
        ProductEntity product = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new ProductDTO(product,product.getCategories());
    }

    @Transactional
    public ProductDTO insert(ProductDTO productDTO) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(productDTO.getName());
        productEntity = repository.save(productEntity);
        return new ProductDTO(productEntity);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO productDTO) {
        try {
            ProductEntity productEntity = repository.getReferenceById(id);
            productEntity.setName(productDTO.getName());
            productEntity = repository.save(productEntity);
            return new ProductDTO(productEntity);
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
