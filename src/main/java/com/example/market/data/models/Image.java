package com.example.market.data.models;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Where(clause = "deleted is distinct from true")
@Data
@Accessors(chain = true)
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "path", nullable = false)
    private String name;

    @Column(name = "deleted")
    private Boolean deleted;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "image")
    private List<Product> products;
}
