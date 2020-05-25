package com.example.market.data.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
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
    private Map<ProductDto, Integer> products;
}
