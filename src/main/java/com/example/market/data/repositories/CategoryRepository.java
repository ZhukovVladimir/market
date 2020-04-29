package com.example.market.data.repositories;

import com.example.market.data.models.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

    @Override
    List<Category> findAll();
}
