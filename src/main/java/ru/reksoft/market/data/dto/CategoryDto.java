package ru.reksoft.market.data.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class CategoryDto {
    @Null(message = "{id.null}")
    private Long id;

    @NotEmpty(message = "{name.notempty}")
    @Size(min = 1, max = 40, message = "{name.size}")
    private String name;

}
