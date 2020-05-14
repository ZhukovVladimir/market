package com.example.market.data.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

@Data
@Accessors(chain = true)
public class ColorDto {
    private Long id;

    @NotEmpty
    private String name;

}
