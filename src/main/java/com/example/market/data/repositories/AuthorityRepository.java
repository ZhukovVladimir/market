package com.example.market.data.repositories;

import com.example.market.data.models.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Set<Authority> findAuthoritiesByAuthority(String name);
}
