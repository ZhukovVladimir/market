package com.example.market.data.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
public class CartDto {
    @NotNull
    private Long id;
    @NotEmpty
    private String deliveryAddress;
    private String deliveryStatus;
    private LocalDateTime creationTime;
    private LocalDateTime paymentTime;
    @NotNull
    private Double bill;
    private List<BookedProductDto> products;
}
