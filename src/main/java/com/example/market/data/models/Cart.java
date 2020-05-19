package com.example.market.data.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

//this is order too
@Entity
@Data
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    //todo should to be enum
    @Column(name = "delivery_status")
    private String deliveryStatus;

    //todo rename to timestamp
    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    @Column(name = "payment_time")
    private LocalDateTime paymentTime;

    //todo change the type
    @Column(name = "bill")
    private Double bill;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "product_cart",
            joinColumns = { @JoinColumn(name = "cart_id") },
            inverseJoinColumns = { @JoinColumn(name = "product_id") }
    )
    private List<Product> products;
}
