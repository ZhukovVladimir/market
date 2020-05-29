package com.example.market.data.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
public class CartDto {
    private Long id;
    private String deliveryAddress;
    private String deliveryStatus;
    private LocalDateTime creationTime;
    private LocalDateTime paymentTime;
    private Double bill;
    private List<BookedProductDto> products;
}
