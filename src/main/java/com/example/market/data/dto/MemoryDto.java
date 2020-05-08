package com.example.market.data.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MemoryDto {
    private Long id;

    @NotNull
    private Integer volume;
}
