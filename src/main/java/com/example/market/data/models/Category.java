package com.example.market.data.models;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

/**
 * entity for working with categories of products
 */
@Entity
@Where(clause = "deleted is distinct from true")
@Data
@Table(name = "category")
@Accessors(chain = true)
public class Category {

    /**
     * id in category table
     * this is generated value
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * name of the category
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * field for soft-delete
     * if it's true - category has been deleted
     */
    @Column(name = "deleted")
    private Boolean deleted;

    /**
     * list of models with the category
     */
    @OneToMany(mappedBy = "category")
    private List<Model> models;
}
