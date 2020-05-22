package com.example.market.data.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;

@Data
@Accessors(chain = true)
public class CategoryDto {
    @Null
    private Long id;

    @NotEmpty
    private String name;

}
