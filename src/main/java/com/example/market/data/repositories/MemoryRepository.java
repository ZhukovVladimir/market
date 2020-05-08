package com.example.market.data.repositories;

import com.example.market.data.models.Memory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemoryRepository extends JpaRepository<Memory, Long> {

    @Override
    List<Memory> findAll();
}
