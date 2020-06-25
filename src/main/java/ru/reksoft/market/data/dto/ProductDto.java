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

    @Null(message = "{id.null}")
    private Long id;

    @NotEmpty(message = "{name.notempty}")
    private String name;

    @NotNull(message = "{price.notnull}")
    private BigDecimal price;

    @NotNull(message = "{count.notnull}")
    private Integer count;

    private String description;

    @NotNull(message = "{color.notnull}")
    private ColorDto color;

    @NotNull(message = "{memory.notnull}")
    private MemoryDto memory;

    @NotNull(message = "{model.notnull}")
    private ModelDto model;

    @NotNull(message = "{image.notnull}")
    private ImageDto image;

    @Null(message = "{creationTime.null}")
    private LocalDateTime creationTime;

}
