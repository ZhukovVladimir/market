package com.example.market.data.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProductDTO {

    private Long id;
    private String name;
    private Double price;
    private Integer count;
    private String image;
    private String description;
    private String colorName;
    private String memoryVolume;
    private String categoryName;
    private String modelName;
}
