package ru.reksoft.market.data.dto;

import lombok.Data;

@Data
public class ProductSearchDto {
    private String name;
    private Double price;
    private ColorDto color;
    private MemoryDto memory;
    private ModelDto model;
    private Boolean available;
}
