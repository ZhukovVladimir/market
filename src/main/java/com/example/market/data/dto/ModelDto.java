package com.example.market.data.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class ModelDto {
    private Long id;

    @NotEmpty
    private String name;

    @NotNull
    private CategoryDto category;

}
