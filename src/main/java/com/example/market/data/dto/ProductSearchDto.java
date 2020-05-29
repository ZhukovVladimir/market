package com.example.market.data.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductSearchDto {
    private String name;
    private Double price;
    private ColorDto color;
    private MemoryDto memory;
    private ModelDto model;
    private Boolean available;
}
