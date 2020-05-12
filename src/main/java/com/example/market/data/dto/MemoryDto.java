package com.example.market.data.dto;

import com.example.market.data.models.Memory;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MemoryDto {
    private Long id;

    @NotNull
    private Integer volume;

    public MemoryDto(Memory memory) {
        this.id = memory.getId();
        this.volume = memory.getVolume();
    }

    public MemoryDto() {

    }
}
