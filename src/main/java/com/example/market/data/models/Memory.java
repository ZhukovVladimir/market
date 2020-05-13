package com.example.market.data.models;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Where(clause = "deleted is distinct from true")
@Data
@Table(name = "memory")
@Accessors(chain = true)
public class Memory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "volume", nullable = false)
    private Integer volume;

    @Column(name = "deleted")
    private Boolean deleted;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "memory")
    private List<Product> products;
}
