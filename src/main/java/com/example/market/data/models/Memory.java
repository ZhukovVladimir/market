package com.example.market.data.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "memory")
public class Memory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "volume", nullable = false)
    private String volume;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "memory")
    private List<Product> products;
}
