package com.example.market.data.dto;

import lombok.Data;

@Data
public class ProductDTO {

    private Integer id;
    private String name;
    private Double price;
    private Integer count;
    private String image;
    private String description;
    private String colorName;
    private String memoryName;
    private String categoryName;
    private String modelName;
}
