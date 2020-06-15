package ru.reksoft.market.data.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductSearchDto {
    private String name;
    private BigDecimal price;
    private ColorDto color;
    private MemoryDto memory;
    private ModelDto model;
    private Boolean available;
}
