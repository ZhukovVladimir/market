package com.example.demo.web.controllers;

import com.example.demo.data.dto.ProductDTO;
import com.example.demo.data.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ViewController {

    private final ProductService productService;

    @Autowired
    public ViewController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String hello(Model model) {
        List<ProductDTO> products = productService.findAll();
        model.addAttribute("products", products);
        return "hello";
    }
}
