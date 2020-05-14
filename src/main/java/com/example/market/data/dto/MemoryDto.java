package com.example.market.data.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class MemoryDto {
    private Long id;

    @NotNull
    private Integer volume;

}
