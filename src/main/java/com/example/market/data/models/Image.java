package com.example.market.data.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "path", nullable = false)
    private String path;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "image")
    private List<Product> products;
}
