package com.example.market.web.controllers.api;

import com.example.market.data.dto.CategoryDto;
import com.example.market.data.dto.ModelDto;
import com.example.market.data.services.CategoryService;
import com.example.market.data.services.ModelService;
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
    private final ModelService modelService;

    @Autowired
    public DictionaryController(CategoryService categoryService, ModelService modelService) {
        this.categoryService = categoryService;
        this.modelService = modelService;
    }

    @GetMapping("/categories")
    public List<CategoryDto> getAllCategories() {
        return categoryService.findAll();
    }

    @GetMapping("/models")
    public List<ModelDto> getAllmodels() {
        return modelService.findAll();
    }
}
