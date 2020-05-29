package com.example.market.data.repository;

import com.example.market.data.model.Memory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemoryRepository extends JpaRepository<Memory, Long> {

    @Override
    List<Memory> findAll();
}
