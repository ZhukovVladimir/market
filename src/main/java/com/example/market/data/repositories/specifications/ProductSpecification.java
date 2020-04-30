package com.example.market.data.repositories.specifications;

import com.example.market.data.dto.ProductSearchDto;
import com.example.market.data.models.Category_;
import com.example.market.data.models.Model_;
import com.example.market.data.models.Product;
import com.example.market.data.models.Product_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

//models, price, checkBox for available
public class ProductSpecification {

    public static Specification<Product> hasModel(ProductSearchDto referenceProduct) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Join<Product_, Model_> productModelJoin = root.join(Product_.MODEL);
            return criteriaBuilder.equal(productModelJoin.get(Product_.NAME), referenceProduct.getModelName());
        };
    }

    public static Specification<Product> hasCategory(ProductSearchDto referenceProduct) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Join<Product_, Category_> productCategoryJoin = root.join(Product_.MODEL).join(Model_.CATEGORY);
            return criteriaBuilder.equal(productCategoryJoin.get(Category_.NAME), referenceProduct.getCategoryName());
        };
    }

    public static Specification<Product> defaultSpecification() {
        return (root, criteriaQuery, criteriaBuilder) -> null;
    }
}
