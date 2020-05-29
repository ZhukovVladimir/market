package com.example.market.data.model;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Accessors(chain = true)
@Entity
@IdClass(BookedProductId.class)
@Table(name = "cart_products")
public class BookedProduct {

    @Id
    @Column(name = "product_id")
    private Long productId;

    @Id
    @Column(name = "cart_id")
    private Long cartId;

    @Column(name = "count")
    private Integer count;

    @OneToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cart_id", insertable = false, updatable = false)
    @ToString.Exclude
    private Cart cart;
}
