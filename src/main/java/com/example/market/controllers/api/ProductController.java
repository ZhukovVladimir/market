package com.example.market.controllers.api;

import com.example.market.controllers.api.CustomAnnotations.ApiPageable;
import com.example.market.data.dto.ProductDto;
import com.example.market.data.dto.ProductSearchDto;
import com.example.market.services.CategoryService;
import com.example.market.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

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
    @ApiPageable
    public Page<ProductDto> getAllProducts(
            @ApiIgnore(
                    "Ignored because swagger ui shows the wrong params, " +
                            "instead they are explained in the implicit params"
            )
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC)
                    Pageable pageable) {
        return productService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PostMapping("/search")
    @ApiPageable
    public Page<ProductDto> getProducts(@RequestBody ProductSearchDto productSearchDto,
                                        @ApiIgnore(
                                                "Ignored because swagger ui shows the wrong params, " +
                                                        "instead they are explained in the implicit params"
                                        )
                                        @PageableDefault(sort = "id", direction = Sort.Direction.DESC)
                                                Pageable pageable) {
        return productService.findAll(productSearchDto, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto createProduct(@Validated @RequestBody ProductDto product) {
        return productService.createProduct(product);
    }

    @PutMapping("/{id}")
    public ProductDto updateProduct(@PathVariable Long id, @Validated @RequestBody ProductDto product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public ProductDto deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }
}
