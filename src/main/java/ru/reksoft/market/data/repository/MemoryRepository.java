package ru.reksoft.market.data.repository;

import ru.reksoft.market.data.model.Memory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemoryRepository extends JpaRepository<Memory, Long> {

    @Override
    List<Memory> findAll();
}
