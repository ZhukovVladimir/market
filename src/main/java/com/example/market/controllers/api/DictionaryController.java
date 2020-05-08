package com.example.market.controllers.api;

import com.example.market.data.dto.*;
import com.example.market.services.CategoryService;
import com.example.market.services.ColorService;
import com.example.market.services.MemoryService;
import com.example.market.services.ModelService;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dictionary")
public class DictionaryController {

    private final CategoryService categoryService;
    private final ModelService modelService;
    private final ColorService colorService;
    private final MemoryService memoryService;

    @Autowired
    public DictionaryController(CategoryService categoryService, ModelService modelService, ColorService colorService,
                                MemoryService memoryService) {
        this.categoryService = categoryService;
        this.modelService = modelService;
        this.colorService = colorService;
        this.memoryService = memoryService;
    }

    @GetMapping("/categories")
    public List<CategoryDto> getAllCategories() {
        return categoryService.findAll();
    }

    @PostMapping("/category")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@Validated @RequestBody CategoryDto categoryDto) {
        return categoryService.createCategory(categoryDto);
    }

    @PutMapping("/category/{id}")
    public CategoryDto updateCategory(@PathVariable Long id, @Validated @RequestBody CategoryDto categoryDto) {
        return categoryService.updateCategory(id, categoryDto);
    }

    @DeleteMapping("/category/{id}")
    public CategoryDto deleteCategory(@PathVariable Long id) {
        return categoryService.deleteCategory(id);
    }

    @GetMapping("/models")
    public List<ModelDto> getAllModels() {
        return modelService.findAll();
    }

    @PostMapping("/model")
    @ResponseStatus(HttpStatus.CREATED)
    public ModelDto createModel(@Validated @RequestBody ModelDto modelDto) {
        return modelService.createModel(modelDto);
    }

    @PutMapping("/model/{id}")
    public ModelDto updateModel(@PathVariable Long id, @Validated @RequestBody ModelDto modelDto) {
        return modelService.updateModel(id, modelDto);
    }

    @DeleteMapping("/model/{id}")
    public ModelDto deleteModel(@PathVariable Long id) {
        return modelService.deleteModel(id);
    }

    @GetMapping("/colors")
    public List<ColorDto> getAllColors() {
        return colorService.findAll();
    }

    @PostMapping("/color")
    @ResponseStatus(HttpStatus.CREATED)
    public ColorDto createColor(@Validated @RequestBody ColorDto colorDto) {
        return colorService.createColor(colorDto);
    }

    @PutMapping("/color/{id}")
    public ColorDto updateColor(@PathVariable Long id, @Validated @RequestBody ColorDto colorDto) {
        return colorService.updateColor(id, colorDto);
    }

    @DeleteMapping("/color/{id}")
    public ColorDto deleteColor(@PathVariable Long id) {
        return colorService.deleteColor(id);
    }

    @GetMapping("/storage")
    public List<MemoryDto> getAllMemoryTypes() {
        return memoryService.findAll();
    }

    @PostMapping("/storage")
    @ResponseStatus(HttpStatus.CREATED)
    public MemoryDto createMemory(@Validated @RequestBody MemoryDto memoryDto) {
        return memoryService.createMemory(memoryDto);
    }

    @PutMapping("/storage/{id}")
    public MemoryDto updateMemory(@RequestParam Long id, @Validated @RequestBody MemoryDto memoryDto) {
        return memoryService.updateMemory(id, memoryDto);
    }

    @DeleteMapping("/storage/{id}")
    public MemoryDto deleteMemory(@PathVariable Long id) {
        return memoryService.deleteMemory(id);
    }
}
