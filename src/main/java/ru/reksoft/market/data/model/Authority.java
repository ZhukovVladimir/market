package ru.reksoft.market.data.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

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
    @ToString.Exclude
    private List<User> users;
}
