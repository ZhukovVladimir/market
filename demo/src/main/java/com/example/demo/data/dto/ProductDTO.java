package com.example.demo.data.dto;

import com.example.demo.data.models.Category;
import com.example.demo.data.models.Color;
import com.example.demo.data.models.Memory;
import com.example.demo.data.models.Model;
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
