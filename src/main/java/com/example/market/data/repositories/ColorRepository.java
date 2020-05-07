package com.example.market.data.repositories;

import com.example.market.data.models.Color;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColorRepository extends CrudRepository<Color, Long> {

    @Override
    List<Color> findAll();
}
