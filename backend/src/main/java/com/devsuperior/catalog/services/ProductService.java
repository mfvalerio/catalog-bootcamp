package com.devsuperior.catalog.services;

import com.devsuperior.catalog.dto.CategoryDTO;
import com.devsuperior.catalog.dto.ProductDTO;
import com.devsuperior.catalog.entities.CategoryEntity;
import com.devsuperior.catalog.entities.ProductEntity;
import com.devsuperior.catalog.repositories.CategoryRepository;
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
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(PageRequest pageRequest) {
        Page<ProductEntity> list = productRepository.findAll(pageRequest);

        return list.map(ProductDTO::new);
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<ProductEntity> obj = productRepository.findById(id);
        ProductEntity product = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new ProductDTO(product,product.getCategories());
    }

    @Transactional
    public ProductDTO insert(ProductDTO productDTO) {
        ProductEntity productEntity = new ProductEntity();
        copyDtoToEntity(productDTO, productEntity);
        productEntity = productRepository.save(productEntity);
        return new ProductDTO(productEntity);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO productDTO) {
        try {
            ProductEntity productEntity = productRepository.getReferenceById(id);
            copyDtoToEntity(productDTO, productEntity);
            productEntity = productRepository.save(productEntity);
            return new ProductDTO(productEntity);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found");
        }
    }

    public void delete(Long id) {
        try {
            productRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found");
        }
        catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(ProductDTO productDTO, ProductEntity productEntity) {

        productEntity.setName(productDTO.getName());
        productEntity.setDescription(productDTO.getDescription());
        productEntity.setDate(productDTO.getDate());
        productEntity.setPrice(productDTO.getPrice());
        productEntity.setImgUrl(productDTO.getImgUrl());

        productEntity.getCategories().clear();
        for (CategoryDTO dto : productDTO.getCategories()) {
            CategoryEntity category = categoryRepository.getOne(dto.getId());
            productEntity.getCategories().add(category);
        }

    }
}
