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
            Join<Product_, Model_> productModelJoin = root.join("model");
            return criteriaBuilder.equal(productModelJoin.get("name"), referenceProduct.getModelName());
        };
    }

    public static Specification<Product> hasCategory(ProductSearchDto referenceProduct) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Join<Product_, Category_> productCategoryJoin = root.join("model").join("category");
            return criteriaBuilder.equal(productCategoryJoin.get("name"), referenceProduct.getCategoryName());
        };
    }
}
