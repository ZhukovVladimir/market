package com.example.market.data.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@Accessors(chain = true)
public class BookedProductId implements Serializable {

    private Long productId;
    private Long cartId;
}
