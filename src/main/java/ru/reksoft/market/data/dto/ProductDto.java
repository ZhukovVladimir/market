package ru.reksoft.market.data.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class ProductDto {

    @Null
    private Long id;

    @NotEmpty
    private String name;

    @NotNull
    private BigDecimal price;

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

    @Null
    private LocalDateTime creationTime;

}
