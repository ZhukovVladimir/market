package com.example.demo.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "delivery_address", nullable = false)
    private String deliveryAddress;

    @Column(name = "delivery_status", nullable = false)
    private String deliveryStatus;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToMany
    @JoinTable(
            name = "product_cart",
            joinColumns = { @JoinColumn(name = "cart_id") },
            inverseJoinColumns = { @JoinColumn(name = "product_id") }
    )
    private List<Product> products;
}
