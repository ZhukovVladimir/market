package ru.reksoft.market.controller;

import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import ru.reksoft.market.controller.api.annotation.ApiPageable;
import ru.reksoft.market.data.dto.CategoryDto;
import ru.reksoft.market.data.dto.ProductDto;
import ru.reksoft.market.service.CategoryService;
import ru.reksoft.market.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Controller
public class ViewController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public ViewController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    @ApiPageable
    public String hello(Model model,
                        @ApiIgnore(
                                "Ignored because swagger ui shows the wrong params, " +
                                        "instead they are explained in the implicit params"
                        )
                        @PageableDefault(sort = "id", size = Integer.MAX_VALUE, direction = Sort.Direction.DESC)
                                Pageable pageable) {
        List<ProductDto> products = productService.search(pageable).toList();
        List<CategoryDto> categories = categoryService.findAll();

        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        return "index";
    }
}
