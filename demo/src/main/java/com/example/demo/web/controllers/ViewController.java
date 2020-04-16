package com.example.demo.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ViewController {
    @GetMapping("/products")
    public String hello(Model model) {

        List<String> products = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            products.add("Product number" + i);
        }

        model.addAttribute("products", products);
        return "hello";
    }
}
