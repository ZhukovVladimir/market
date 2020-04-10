package com.example.demo.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone", nullable = false)
    private Integer phone;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_country_code", nullable = false)
    private Integer phoneCountryCode;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "account")
    private List<Cart> carts;
}
