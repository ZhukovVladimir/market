package ru.reksoft.market.controller.api;

import ru.reksoft.market.controller.api.annotation.ApiPageable;
import ru.reksoft.market.data.dto.ProductDto;
import ru.reksoft.market.data.dto.ProductSearchDto;
import ru.reksoft.market.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @ApiPageable
    public Page<ProductDto> getAllProducts(
            @ApiIgnore(
                    "Ignored because swagger ui shows the wrong params, " +
                            "instead they are explained in the implicit params"
            )
            @PageableDefault(sort = "creationTime", size = 6, direction = Sort.Direction.DESC)
                    Pageable pageable) {
        return productService.search(pageable);
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
                                        @PageableDefault(sort = "creationTime", size = 6, direction = Sort.Direction.DESC)
                                                Pageable pageable) {
        return productService.search(productSearchDto, pageable);
    }

}
