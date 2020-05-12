package com.example.market.services;

import com.example.market.data.dto.ProductDto;
import com.example.market.data.dto.ProductSearchDto;
import com.example.market.data.models.Product;
import com.example.market.data.repositories.ProductRepository;
import com.example.market.exceptions.ConflictException;
import com.example.market.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.market.data.repositories.specifications.ProductSpecification.*;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelService modelService;
    private final ColorService colorService;
    private final MemoryService memoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ModelService modelService, ColorService colorService,
                          MemoryService memoryService, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelService = modelService;
        this.colorService = colorService;
        this.memoryService = memoryService;
        this.modelMapper = modelMapper;
    }

    private List<ProductDto> productsToProductsDto(List<Product> products) {
        return products.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    public Page<ProductDto> findAll(Pageable pageable) {
               return productRepository.findAll(defaultSpecification(), pageable).map(ProductDto::new);
    }

    public Page<ProductDto> findAll(ProductSearchDto productSearchDto, Pageable pageable) {
        Specification<Product> specification = defaultSpecification();
        if (productSearchDto.getModel() != null && productSearchDto.getModel().getCategory() != null
                && productSearchDto.getModel().getCategory().getName() != null) {
            specification = specification.and(hasCategory(productSearchDto));
        }
        if (productSearchDto.getModel() != null && productSearchDto.getModel().getName() != null) {
            specification = specification.and(hasModel(productSearchDto));
        }
        if (productSearchDto.getPrice() != null) {
            specification = specification.and(priceLessThen(productSearchDto));
        }
        if (productSearchDto.getAvailable() != null && productSearchDto.getAvailable()) {
            specification = specification.and(isAvailable());
        }
        return productRepository.findAll(specification, pageable).map(ProductDto::new);
    }

    public ProductDto createProduct(ProductDto productDto) {
        if (productDto.getId() != null && productRepository.existsById(productDto.getId())) {
            throw new ConflictException("Product is already exist");
        } else {
            Product product = modelMapper.map(productDto, Product.class);
            product = productRepository.save(product);
            return modelMapper.map(product, ProductDto.class);
        }
    }

    //todo: should use to specifications, orders by (default, name, price), filters ()
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        if (productRepository.existsById(id)) {
            Product product = modelMapper.map(productDto, Product.class).setId(id);
            product = productRepository.save(product);
            return modelMapper.map(product, ProductDto.class);
        }
        throw new ResourceNotFoundException(id, "Product");
    }

    public ProductDto findById(Long id) {
        Specification<Product> specification = defaultSpecification().and(hasId(id));
        return modelMapper.map(productRepository.findOne(specification)
                        .orElseThrow(ResourceNotFoundException::new),
                ProductDto.class);
    }

    public ProductDto deleteProduct(Long id) {
        Specification<Product> specification = defaultSpecification().and(hasId(id));
        Product product = productRepository.findOne(specification)
                .orElseThrow(ResourceNotFoundException::new);

        product = productRepository.save(product.setDeleted(true));
        return modelMapper.map(product, ProductDto.class);
    }
}
