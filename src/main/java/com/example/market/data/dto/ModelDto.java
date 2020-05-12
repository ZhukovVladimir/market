package com.example.market.data.dto;

import com.example.market.data.models.Model;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ModelDto {
    private Long id;

    @NotEmpty
    private String name;

    @NotNull
    private CategoryDto category;

    public ModelDto(Model model) {
        this.id = model.getId();
        this.name = model.getName();
        this.category = new CategoryDto(model.getCategory());
    }

    public ModelDto() {

    }
}
