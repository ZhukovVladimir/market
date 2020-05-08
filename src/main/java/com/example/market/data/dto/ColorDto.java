package com.example.market.data.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ColorDto {
    private Long id;

    @NotEmpty
    private String name;
}
