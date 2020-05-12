package com.example.market.controllers;

import com.example.market.data.dto.CategoryDto;
import com.example.market.data.dto.ProductDto;
import com.example.market.services.CategoryService;
import com.example.market.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
    public String hello(Model model, Pageable pageable) {
        List<ProductDto> products = productService.findAll(pageable).toList();
        List<CategoryDto> categories = categoryService.findAll();

        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        return "index";
    }
}
