package com.example.market.data.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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
    //todo should to be enum
    @Column(name = "delivery_status")
    private String deliveryStatus;

    /**
     * time of creation the order
     */
    //todo rename to timestamp
    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    /**
     * payment time
     */
    @Column(name = "payment_time")
    private LocalDateTime paymentTime;

    /**
     * bill of the cart
     */
    //todo change the type
    @Column(name = "bill")
    private Double bill;

    /**
     * user of the cart
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * list of products into the cart
     */
    @ManyToMany
    @JoinTable(
            name = "product_cart",
            joinColumns = { @JoinColumn(name = "cart_id") },
            inverseJoinColumns = { @JoinColumn(name = "product_id") }
    )
    private List<Product> products;
}
