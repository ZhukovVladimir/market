package ru.reksoft.market.controller.api.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import ru.reksoft.market.data.dto.ProductDto;
import ru.reksoft.market.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "/api/admin/products", tags = {"Контроллер для редактирования продуктов"})
@RestController
@RequestMapping(value = "/api/admin/products")
public class ProductAdminController {

    private final ProductService productService;

    @Autowired
    public ProductAdminController(ProductService productService) {
        this.productService = productService;
    }

    @ApiOperation(value = "Создание продукта")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto createProduct(@Validated @RequestBody ProductDto product) {
        return productService.createProduct(product);
    }

    @ApiOperation(value = "Редактирование продукта")
    @PutMapping("/{id}")
    public ProductDto updateProduct(@PathVariable Long id, @Validated @RequestBody ProductDto product) {
        return productService.updateProduct(id, product);
    }

    @ApiOperation(value = "Удаление продукта")
    @DeleteMapping("/{id}")
    public ProductDto deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }
}
