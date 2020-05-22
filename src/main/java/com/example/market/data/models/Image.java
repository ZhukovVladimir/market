package com.example.market.data.models;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

/**
 * entity for working with images of products
 */
@Entity
@Where(clause = "deleted is distinct from true")
@Data
@Accessors(chain = true)
@Table(name = "image")
public class Image {

    /**
     * id in image table
     * this is generated value
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * name of the image
     */
    @Column(name = "path", nullable = false)
    private String name;

    /**
     * field for soft-delete
     * if it's true - image has been deleted
     */
    @Column(name = "deleted")
    private Boolean deleted;

    /**
     * list of products with the image
     */
    @OneToMany(mappedBy = "image")
    private List<Product> products;
}
