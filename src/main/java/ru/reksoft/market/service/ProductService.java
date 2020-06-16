package ru.reksoft.market.service;

import ru.reksoft.market.data.dto.ProductDto;
import ru.reksoft.market.data.dto.ProductSearchDto;
import ru.reksoft.market.data.model.Product;
import ru.reksoft.market.data.repository.ProductRepository;
import ru.reksoft.market.exception.BadRequestException;
import ru.reksoft.market.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.reksoft.market.data.repository.specification.ProductSpecification.*;

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

    public Page<ProductDto> search(Pageable pageable) {
        return productRepository.findAll(defaultSpecification(), pageable).
                map(product -> modelMapper.map(product, ProductDto.class));
    }

    public Page<ProductDto> search(ProductSearchDto productSearchDto, Pageable pageable) {
        Specification<Product> specification = defaultSpecification();
        if (productSearchDto.getModel() != null && productSearchDto.getModel().getCategory() != null
                && productSearchDto.getModel().getCategory().getName() != null) {
            specification = specification.and(hasCategory(productSearchDto));
        }
        if (productSearchDto.getColor() != null && productSearchDto.getColor().getName() != null) {
            specification = specification.and(hasColor(productSearchDto));
        }
        if (productSearchDto.getModel() != null && productSearchDto.getModel().getName() != null) {
            specification = specification.and(hasModel(productSearchDto));
        }
        if (productSearchDto.getMemory() != null && productSearchDto.getMemory().getVolume() != null) {
            specification = specification.and(hasMemory(productSearchDto));
        }
        if (productSearchDto.getMaxPrice() != null) {
            specification = specification.and(priceLessThen(productSearchDto));
        }
        if (productSearchDto.getMinPrice() != null) {
            specification = specification.and(priceHigherThen(productSearchDto));
        }
        if (productSearchDto.getAvailable() != null && productSearchDto.getAvailable()) {
            specification = specification.and(isAvailable());
        }
        return productRepository.findAll(specification, pageable)
                .map(product -> modelMapper.map(product, ProductDto.class));
    }

    public ProductDto createProduct(ProductDto productDto) {
        if (productDto.getId() != null) {
            throw new BadRequestException("Id should be null");
        } else {
            productDto.setCreationTime(LocalDateTime.now());
            Product product = modelMapper.map(productDto, Product.class);
            product = productRepository.save(product);
            return modelMapper.map(product, ProductDto.class);
        }
    }

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
