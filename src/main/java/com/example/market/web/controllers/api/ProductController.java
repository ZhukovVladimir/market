package com.example.market.web.controllers.api;

import com.example.market.data.dto.ProductDto;
import com.example.market.data.dto.ProductSearchDto;
import com.example.market.data.services.CategoryService;
import com.example.market.data.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//todo: add and edit products

@RestController
@RequestMapping(value = "/api/product")
public class ProductController {

    private final ProductService productService;

    private final CategoryService categoryService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public List<ProductDto> getAllProducts() {
        return productService.findAll();
    }

    @PostMapping("/search")
    public List<ProductDto> getProducts(@RequestBody ProductSearchDto productSearchDto) {
        return productService.findAll(productSearchDto);
    }

    @PostMapping
    public ProductDto saveProduct(@RequestBody ProductDto product) {
        return productService.saveProduct(product);
    }
}
