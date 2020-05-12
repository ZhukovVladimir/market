package com.example.market.data.dto;

import com.example.market.data.models.Product;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class ProductDto {
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private Double price;

    @NotNull
    private Integer count;

    private String description;

    @NotNull
    private ColorDto color;

    @NotNull
    private MemoryDto memory;

    @NotNull
    private ModelDto model;

    @NotNull
    private ImageDto image;

    public ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.count = product.getCount();
        this.description = product.getDescription();
        this.color = new ColorDto(product.getColor());
        this.memory = new MemoryDto(product.getMemory());
        this.model = new ModelDto(product.getModel());
        this.image = new ImageDto(product.getImage());
    }

    public ProductDto() {

    }
}
