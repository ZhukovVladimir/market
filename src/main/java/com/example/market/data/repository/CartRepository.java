package com.example.market.data.repository;

import com.example.market.data.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByUserId(Long id);

    Optional<Cart> findByIdAndUserId(Long cartId, Long userId);
}
