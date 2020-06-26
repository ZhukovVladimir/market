package ru.reksoft.market.data.repository;

import org.springframework.data.domain.Pageable;
import ru.reksoft.market.data.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.reksoft.market.data.model.DeliveryStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByUserId(Long id, Pageable pageable);

    Optional<Cart> findByIdAndUserId(Long cartId, Long userId);

    Optional<List<Cart>> findAllByDeliveryStatus(DeliveryStatus deliveryStatus);
}
