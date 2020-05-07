package com.example.market.web.controllers.api;

import com.example.market.data.dto.CategoryDto;
import com.example.market.data.dto.ColorDto;
import com.example.market.data.dto.MemoryDto;
import com.example.market.data.dto.ModelDto;
import com.example.market.data.services.CategoryService;
import com.example.market.data.services.ColorService;
import com.example.market.data.services.MemoryService;
import com.example.market.data.services.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public CategoryDto saveCategory(@RequestBody CategoryDto categoryDto) {
        return categoryService.saveCategory(categoryDto);
    }

    @GetMapping("/models")
    public List<ModelDto> getAllmodels() {
        return modelService.findAll();
    }

    @PostMapping("/model")
    public ModelDto saveModel(@RequestBody ModelDto modelDto) {
        return modelService.saveModel(modelDto);
    }

    @GetMapping("/colors")
    public List<ColorDto> getAllColors() {
        return colorService.findAll();
    }

    @PostMapping("/color")
    public ColorDto saveColor(@RequestBody ColorDto colorDto) {
        return colorService.saveColor(colorDto);
    }

    @GetMapping("/storage")
    public List<MemoryDto> getAllMemoryTypes() {
        return memoryService.findAll();
    }

    @PostMapping("/storage")
    public MemoryDto saveMemory(@RequestBody MemoryDto memoryDto) {
        return memoryService.saveMemory(memoryDto);
    }
}
