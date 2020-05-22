package com.example.market.data.models;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

/**
 * entity for working with colors of products
 */
@Entity
@Where(clause = "deleted is distinct from true")
@Data
@Table(name = "color")
@Accessors(chain = true)
public class Color {

    /**
     * id in color table
     * this is generated value
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * name of the color
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * field for soft-delete
     * if it's true - color has been deleted
     */
    @Column(name = "deleted")
    private Boolean deleted;

    /**
     * list of products with the color
     */
    @OneToMany(mappedBy = "color")
    private List<Product> products;
}
