package com.example.market.data.dto;

import com.example.market.data.models.Category;
import com.example.market.data.models.Model;
import lombok.Data;

@Data
public class ProductSearchDto {
    private String name;
    private Double price;
    private String colorName;
    private String memoryVolume;
    private String categoryName;
    private String modelName;
    private Boolean available;
}
