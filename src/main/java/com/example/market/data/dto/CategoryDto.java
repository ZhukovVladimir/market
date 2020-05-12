package com.example.market.data.dto;

import com.example.market.data.models.Category;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CategoryDto {
    private Long id;

    @NotEmpty
    private String name;

    public CategoryDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }

    public CategoryDto() {

    }
}
