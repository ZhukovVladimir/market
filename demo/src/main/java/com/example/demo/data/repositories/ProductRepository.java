package com.example.demo.data.repositories;

import com.example.demo.data.models.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

    @Override
    List<Product> findAll();
}
