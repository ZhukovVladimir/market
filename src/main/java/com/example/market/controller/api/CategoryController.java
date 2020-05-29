package com.example.market.controller.api;

import com.example.market.data.dto.CategoryDto;
import com.example.market.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dictionary/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * GET method for category list
     *
     * @return list with all categories
     */
    @GetMapping
    public List<CategoryDto> getAllCategories() {
        return categoryService.findAll();
    }

}
