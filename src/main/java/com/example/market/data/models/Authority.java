package com.example.market.data.models;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * entity for working with users roles
 */
@Entity
@Data
@Table(name = "authority")
public class Authority implements GrantedAuthority {

    /**
     * id in authority table
     * this is generated value
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * not null field
     * name of users role
     */
    @Column(name = "authority", nullable = false)
    private String authority;

    /**
     * users with the role
     */
    @ManyToMany(mappedBy = "authorities")
    private List<User> users;
}
