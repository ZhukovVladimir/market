package com.example.market.data.repositories;

import com.example.market.data.models.Memory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemoryRepository extends CrudRepository<Memory, Integer> {
}
