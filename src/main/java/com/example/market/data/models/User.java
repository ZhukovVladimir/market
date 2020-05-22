package com.example.market.data.models;

import lombok.Data;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * entity for working with users
 */
@Entity
@Where(clause = "deleted is distinct from true")
@Data
@Table(name = "\"user\"")
public class User implements UserDetails {

    /**
     * id in product table
     * this is generated value
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * password of the user
     */
    @Column(name = "password")
    private String password;

    /**
     * username of the user (email)
     */
    @Column(name = "email", nullable = false)
    private String username;

    /**
     * first name of the user
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * last name of the user
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * phone of the user (without country code)
     */
    @Column(name = "phone", nullable = false)
    private Long phone;

    /**
     * address of the user
     */
    @Column(name = "address")
    private String address;

    /**
     * phone country code for the user
     */
    @Column(name = "phone_country_code", nullable = false)
    private Integer phoneCountryCode;


    /**
     * field for soft-delete
     * if it's true - user has been deleted
     */
    @Column(name = "deleted")
    private Boolean deleted;

    /**
     * set of authorities for the user
     * (auth roles)
     */
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Authority> authorities;

    /**
     * list of carts for the user
     */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<Cart> carts;
    
    public String getEmail() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
