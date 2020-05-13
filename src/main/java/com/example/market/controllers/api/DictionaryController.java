package com.example.market.controllers.api;

import com.example.market.data.dto.CategoryDto;
import com.example.market.data.dto.ColorDto;
import com.example.market.data.dto.MemoryDto;
import com.example.market.data.dto.ModelDto;
import com.example.market.services.CategoryService;
import com.example.market.services.ColorService;
import com.example.market.services.MemoryService;
import com.example.market.services.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CRUD for Product elements (Category, Color, Memory, Model)
 */
@RestController
@RequestMapping("/dictionary")
public class DictionaryController {

    private final CategoryService categoryService;
    private final ModelService modelService;
    private final ColorService colorService;
    private final MemoryService memoryService;

    /**
     * Constructor with all fields
     * @param categoryService
     * @param modelService
     * @param colorService
     * @param memoryService
     */
    @Autowired
    public DictionaryController(CategoryService categoryService, ModelService modelService, ColorService colorService,
                                MemoryService memoryService) {
        this.categoryService = categoryService;
        this.modelService = modelService;
        this.colorService = colorService;
        this.memoryService = memoryService;
    }

    /**
     * GET method for category list
     * @return list with all categories
     */
    @GetMapping("/categories")
    public List<CategoryDto> getAllCategories() {
        return categoryService.findAll();
    }

    /**
     * POST method for creation the category
     * @param categoryDto - category that should be saved
     * @return saved category
     */
    @PostMapping("/category")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@Validated @RequestBody CategoryDto categoryDto) {
        return categoryService.createCategory(categoryDto);
    }

    /**
     * PUT method for editing the category
     * @param id - id of the category that should be updated
     * @param categoryDto - new category fields
     * @return updated category
     */
    @PutMapping("/category/{id}")
    public CategoryDto updateCategory(@PathVariable Long id, @Validated @RequestBody CategoryDto categoryDto) {
        return categoryService.updateCategory(id, categoryDto);
    }

    /**
     * DELETE method for the category
     * @param id - id of the category that should be deleted
     * @return deleted category
     */
    @DeleteMapping("/category/{id}")
    public CategoryDto deleteCategory(@PathVariable Long id) {
        return categoryService.deleteCategory(id);
    }

    /**
     * GET method for model list
     * @return list with all models
     */
    @GetMapping("/models")
    public List<ModelDto> getAllModels() {
        return modelService.findAll();
    }

    /**
     * POST method for creation the model
     * @param modelDto - model that should be saved
     * @return saved model
     */
    @PostMapping("/model")
    @ResponseStatus(HttpStatus.CREATED)
    public ModelDto createModel(@Validated @RequestBody ModelDto modelDto) {
        return modelService.createModel(modelDto);
    }

    /**
     * PUT method for editing the model
     * @param id - id of the model that should be updated
     * @param modelDto - new model fields
     * @return updated model
     */
    @PutMapping("/model/{id}")
    public ModelDto updateModel(@PathVariable Long id, @Validated @RequestBody ModelDto modelDto) {
        return modelService.updateModel(id, modelDto);
    }

    /**
     * DELETE method for the model
     * @param id - id of the model that should be deleted
     * @return deleted model
     */
    @DeleteMapping("/model/{id}")
    public ModelDto deleteModel(@PathVariable Long id) {
        return modelService.deleteModel(id);
    }

    /**
     * GET method for color list
     * @return list with all colors
     */
    @GetMapping("/colors")
    public List<ColorDto> getAllColors() {
        return colorService.findAll();
    }

    /**
     * POST method for creation the color
     * @param colorDto - color that should be saved
     * @return saved color
     */
    @PostMapping("/color")
    @ResponseStatus(HttpStatus.CREATED)
    public ColorDto createColor(@Validated @RequestBody ColorDto colorDto) {
        return colorService.createColor(colorDto);
    }

    /**
     * PUT method for editing the color
     * @param id - id of the color that should be updated
     * @param colorDto - new color fields
     * @return updated color
     */
    @PutMapping("/color/{id}")
    public ColorDto updateColor(@PathVariable Long id, @Validated @RequestBody ColorDto colorDto) {
        return colorService.updateColor(id, colorDto);
    }

    /**
     * DELETE method for the color
     * @param id - id of the color that should be deleted
     * @return deleted color
     */
    @DeleteMapping("/color/{id}")
    public ColorDto deleteColor(@PathVariable Long id) {
        return colorService.deleteColor(id);
    }

    /**
     * GET method for memory list
     * @return list with all types of memory
     */
    @GetMapping("/storage")
    public List<MemoryDto> getAllMemoryTypes() {
        return memoryService.findAll();
    }

    /**
     * POST method for creation the memory
     * @param memoryDto - memory that should be saved
     * @return saved memory
     */
    @PostMapping("/storage")
    @ResponseStatus(HttpStatus.CREATED)
    public MemoryDto createMemory(@Validated @RequestBody MemoryDto memoryDto) {
        return memoryService.createMemory(memoryDto);
    }

    /**
     * PUT method for editing the memory
     * @param id - id of the memory that should be updated
     * @param memoryDto - new memory fields
     * @return updated memory
     */
    @PutMapping("/storage/{id}")
    public MemoryDto updateMemory(@RequestParam Long id, @Validated @RequestBody MemoryDto memoryDto) {
        return memoryService.updateMemory(id, memoryDto);
    }

    /**
     * DELETE method for the memory
     * @param id - id of the memory that should be deleted
     * @return deleted memory
     */
    @DeleteMapping("/storage/{id}")
    public MemoryDto deleteMemory(@PathVariable Long id) {
        return memoryService.deleteMemory(id);
    }
}
