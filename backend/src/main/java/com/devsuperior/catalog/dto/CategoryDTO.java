package com.devsuperior.catalog.dto;

import com.devsuperior.catalog.domain.CategoryEntity;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO implements Serializable {
    private static final Long serialVersionUID = 1L;

    private Long id;
    private String name;

    public CategoryDTO(CategoryEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }
}
