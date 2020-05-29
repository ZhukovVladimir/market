package com.example.market.data.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * entity for working with models of products
 */
@Entity
@Where(clause = "deleted is distinct from true")
@Data
@Table(name = "model")
@Accessors(chain = true)
public class Model {

    /**
     * id in model table
     * this is generated value
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * name of the model
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * field for soft-delete
     * if it's true - model has been deleted
     */
    @Column(name = "deleted")
    private Boolean deleted;

    /**
     * category of the model
     */
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

//    /**
//     * list of products with the model
//     */
//    @OneToMany(mappedBy = "model")
//    private List<Product> products;
}
