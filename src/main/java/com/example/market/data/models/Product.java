package com.example.market.data.models;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Accessors(chain = true)
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    //todo change the type
    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "count", nullable = false)
    private Integer count;

    @Column(name = "description")
    private String description;

    @Column(name = "deleted")
    private Boolean deleted;

    @ManyToOne
    @JoinColumn(name = "color_id", nullable = false)
    private Color color;

    @ManyToOne
    @JoinColumn(name = "memory_id", nullable = false)
    private Memory memory;

    @ManyToMany(mappedBy = "products")
    private List<Cart> carts;

    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false)
    private Model model;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;
}