package com.example.market.data.repository;

import com.example.market.data.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {

    @Override
    List<Model> findAll();
}
