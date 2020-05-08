package com.example.market.data.dto;

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
}
