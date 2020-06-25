package ru.reksoft.market.data.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class MemoryDto {
    @Null(message = "{id.null}")
    private Long id;

    @NotNull(message = "{name.notempty}")
    private Integer volume;

}
