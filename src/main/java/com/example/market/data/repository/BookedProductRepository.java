package com.example.market.data.repository;

import com.example.market.data.model.BookedProduct;
import com.example.market.data.model.BookedProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookedProductRepository extends JpaRepository<BookedProduct, BookedProductId> {
}
