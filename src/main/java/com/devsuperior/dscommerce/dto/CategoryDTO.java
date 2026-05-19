package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.entities.Category;

/*Classe criada para podermos fazer a lista de categorias no ProductDTO*/
public class CategoryDTO {
    private Long id;
    private String name;

    public CategoryDTO() {
    }

    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryDTO(Category entity) {
        id = entity.getId();
        name = entity.getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
