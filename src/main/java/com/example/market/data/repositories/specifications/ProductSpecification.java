package com.example.market.data.repositories.specifications;

import com.example.market.data.dto.ProductSearchDto;
import com.example.market.data.models.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

public class ProductSpecification {

    public static Specification<Product> hasModel(ProductSearchDto referenceProduct) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Join<Product_, Model_> productModelJoin = root.join(Product_.MODEL);
            return criteriaBuilder.equal(productModelJoin.get(Model_.NAME), referenceProduct.getModel().getName());
        };
    }

    public static Specification<Product> hasCategory(ProductSearchDto referenceProduct) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Join<Product_, Category_> productCategoryJoin = root.join(Product_.MODEL).join(Model_.CATEGORY);
            return criteriaBuilder.equal(productCategoryJoin.get(Category_.NAME), referenceProduct.getModel().getCategory().getName());
        };
    }

    public static Specification<Product> hasColor(ProductSearchDto referenceProduct) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Join<Product_, Color_> productColorJoin = root.join(Product_.COLOR);
            return criteriaBuilder.equal(productColorJoin.get(Color_.NAME), referenceProduct.getColor().getName());
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

    public static Specification<Product> hasId(Long id) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(Product_.ID), id);
        };
    }

    public static Specification<Product> defaultSpecification() {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.or(
                    criteriaBuilder.equal(root.get(Product_.DELETED), false),
                    criteriaBuilder.isNull(root.get(Product_.DELETED)));
        };
    }
}
