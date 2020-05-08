package com.example.market.data.repositories.specifications;

import com.example.market.data.dto.ProductSearchDto;
import com.example.market.data.models.Category_;
import com.example.market.data.models.Model_;
import com.example.market.data.models.Product;
import com.example.market.data.models.Product_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

public class ProductSpecification {

    public static Specification<Product> hasModel(ProductSearchDto referenceProduct) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Join<Product_, Model_> productModelJoin = root.join(Product_.MODEL);
            return criteriaBuilder.equal(productModelJoin.get(Product_.NAME), referenceProduct.getModel().getName());
        };
    }

    public static Specification<Product> hasCategory(ProductSearchDto referenceProduct) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Join<Product_, Category_> productCategoryJoin = root.join(Product_.MODEL).join(Model_.CATEGORY);
            return criteriaBuilder.equal(productCategoryJoin.get(Category_.NAME), referenceProduct.getModel().getCategory().getName());
        };
    }

    public static Specification<Product> priceLessThen(ProductSearchDto referenceProduct) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.le(root.get(Product_.PRICE), referenceProduct.getPrice());
        };
    }

    public static Specification<Product> isAvailable() {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.greaterThan(root.get(Product_.COUNT), 0);
        };
    }

    public static Specification<Product> defaultSpecification() {
        return (root, criteriaQuery, criteriaBuilder) -> null;
    }
}
