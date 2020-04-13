package com.example.demo.data.repositories;

import com.example.demo.data.models.Memory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemoryRepository extends CrudRepository<Memory, Integer> {
}
