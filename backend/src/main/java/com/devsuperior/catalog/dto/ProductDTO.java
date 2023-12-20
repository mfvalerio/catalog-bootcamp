package com.devsuperior.catalog.dto;

import com.devsuperior.catalog.entities.CategoryEntity;
import com.devsuperior.catalog.entities.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDTO implements Serializable {
    private static final Long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String description;

    private Double price;

    private String imgUrl;

    private Instant date;

    private List<CategoryDTO> categories = new ArrayList<>();

    public ProductDTO(ProductEntity product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.imgUrl = product.getImgUrl();
        this.date = product.getDate();
    }

    public ProductDTO(ProductEntity product, Set<CategoryEntity> categories) {
        this(product);
        categories.forEach(cat -> this.categories.add((new CategoryDTO(cat))));
    }
}
