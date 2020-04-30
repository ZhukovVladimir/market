package com.example.market.data.repositories;

import com.example.market.data.models.Model;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelRepository extends CrudRepository<Model, Long> {

    @Override
    List<Model> findAll();
}
