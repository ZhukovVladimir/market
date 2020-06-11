package ru.reksoft.market.data.repository;

import ru.reksoft.market.data.model.BookedProduct;
import ru.reksoft.market.data.model.BookedProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookedProductRepository extends JpaRepository<BookedProduct, BookedProductId> {
}
