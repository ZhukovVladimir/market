package ru.reksoft.market.data.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
@Accessors(chain = true)
public class ModelDto {
    @Null(message = "{id.null}")
    private Long id;

    @NotEmpty(message = "{name.notempty}")
    private String name;

    @NotNull(message = "{category.notnull}")
    private CategoryDto category;

}
