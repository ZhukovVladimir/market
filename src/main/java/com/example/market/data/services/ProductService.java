package com.example.market.data.services;

import com.example.market.data.dto.ProductDto;
import com.example.market.data.dto.ProductSearchDto;
import com.example.market.data.models.Product;
import com.example.market.data.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.market.data.repositories.specifications.ProductSpecification.hasCategory;
import static com.example.market.data.repositories.specifications.ProductSpecification.hasModel;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    private List<ProductDto> productsToProductsDto(List<Product> products) {
        return products.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    public List<ProductDto> findAll() {
        return productsToProductsDto(productRepository.findAll());
    }

    public List<ProductDto> findAll(ProductSearchDto productSearchDto) {
        return productsToProductsDto(productRepository.findAll(hasCategory(productSearchDto)));
    }
}
