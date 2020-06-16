package ru.reksoft.market.data.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductSearchDto {
    private String name;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private ColorDto color;
    private MemoryDto memory;
    private ModelDto model;
    private Boolean available;
}
