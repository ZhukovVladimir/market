package com.example.market.data.models;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

/**
 * entity for working with memory of products
 */
@Entity
@Where(clause = "deleted is distinct from true")
@Data
@Table(name = "memory")
@Accessors(chain = true)
public class Memory {

    /**
     * id in memory table
     * this is generated value
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * volume of the memory
     */
    @Column(name = "volume", nullable = false)
    private Integer volume;

    /**
     * field for soft-delete
     * if it's true - memory has been deleted
     */
    @Column(name = "deleted")
    private Boolean deleted;

    /**
     * list of products with the memory
     */
    @OneToMany(mappedBy = "memory")
    private List<Product> products;
}
