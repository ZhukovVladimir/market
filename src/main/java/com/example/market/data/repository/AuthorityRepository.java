package com.example.market.data.repository;

import com.example.market.data.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Set<Authority> findAuthoritiesByAuthority(String name);
}
