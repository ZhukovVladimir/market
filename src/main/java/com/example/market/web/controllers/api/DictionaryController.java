package com.example.market.web.controllers.api;

import com.example.market.data.dto.CategoryDto;
import com.example.market.data.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dictionary")
public class DictionaryController {

    private final CategoryService categoryService;

    @Autowired
    public DictionaryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public List<CategoryDto> getAllCategories() {
        return categoryService.findAll();
    }
}
