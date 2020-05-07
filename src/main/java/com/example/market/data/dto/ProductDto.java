package com.example.market.data.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProductDto {
    private Long id;
    private String name;
    private Double price;
    private Integer count;
    private String description;
    private Long colorId;
    private String colorName;
    private Long memoryId;
    private Integer memoryVolume;
    private Long modelCategoryId;
    private String modelCategoryName;
    private Long modelId;
    private String modelName;
    private Long imageId;
}
