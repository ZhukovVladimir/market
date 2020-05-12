package com.example.market.data.dto;

import com.example.market.data.models.Color;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ColorDto {
    private Long id;

    @NotEmpty
    private String name;

    public ColorDto(Color color) {
        this.id = color.getId();
        this.name = color.getName();
    }

    public ColorDto() {

    }
}
