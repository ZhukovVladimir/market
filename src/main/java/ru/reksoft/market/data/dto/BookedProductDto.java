package ru.reksoft.market.data.dto;

import lombok.Data;

@Data
public class BookedProductDto {

    private ProductDto product;
    private Integer count;
}
