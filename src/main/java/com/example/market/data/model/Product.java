package com.example.market.data.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * entity for working with products
 */
@Entity
@Data
@Accessors(chain = true)
@Table(name = "product")
public class Product {

    /**
     * id in product table
     * this is generated value
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * name of the product
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * price of the product
     */
    @Column(name = "price", nullable = false)
    private Double price;

    /**
     * count of the product
     */
    @Column(name = "count", nullable = false)
    private Integer count;

    /**
     * description of the product
     */
    @Column(name = "description")
    private String description;

    /**
     * field for soft-delete
     * if it's true - product has been deleted
     */
    @Column(name = "deleted")
    private Boolean deleted;

    /**
     * color of the product
     */
    @ManyToOne
    @JoinColumn(name = "color_id", nullable = false)
//    @ToString.Exclude
    private Color color;

    /**
     * memory type of the product
     */
    @ManyToOne
    @JoinColumn(name = "memory_id", nullable = false)
//    @ToString.Exclude
    private Memory memory;

    /**
     * model of the product
     */
    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false)
//    @ToString.Exclude
    private Model model;

    /**
     * image of the product
     */
    @ManyToOne
    @JoinColumn(name = "image_id")
//    @ToString.Exclude
    private Image image;
}