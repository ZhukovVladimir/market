package com.example.market.data.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class MemoryDto {
    @Null
    private Long id;

    @NotNull
    private Integer volume;

}
