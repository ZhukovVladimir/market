package com.example.market.data.dto;

import com.example.market.data.models.Image;
import lombok.Data;

@Data
public class ImageDto {
    private Long id;
    private String name;

    public ImageDto(Image image) {
        this.id = image.getId();
        this.name = image.getName();
    }

    public ImageDto() {

    }
}
