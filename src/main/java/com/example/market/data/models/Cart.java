package com.example.market.data.models;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * entity for working with cart and orders
 */
@Entity
@Data
@Table(name = "cart")
public class Cart {

    /**
     * id in cart table
     * this is generated value
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * delivery address for the order
     */
    @Column(name = "delivery_address")
    private String deliveryAddress;

    /**
     * delivery status for order
     * if delivery status "pre-order" - this entity is cart
     * else - this entity is order
     */
    @Column(name = "delivery_status")
    @Enumerated(EnumType.ORDINAL)
    private DeliveryStatus deliveryStatus;

    /**
     * time of creation the order
     */
    @Column(name = "creation_time")
    private LocalDateTime timestamp;

    /**
     * payment time
     */
    @Column(name = "payment_time")
    private LocalDateTime paymentTime;

    /**
     * bill of the cart
     */
    @Column(name = "bill")
    private Double bill = 0d;

    /**
     * user of the cart
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    /**
     * map of products into the cart
     */
    @ElementCollection
    @CollectionTable(name = "product_cart", joinColumns = @JoinColumn(name = "cart_id"))
    @MapKeyJoinColumn(name = "product_id")
    @Column(name = "count")
    @ToString.Exclude
    private Map<Product, Integer> products = new HashMap<>();
}
