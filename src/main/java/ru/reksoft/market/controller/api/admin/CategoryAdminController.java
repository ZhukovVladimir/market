package ru.reksoft.market.controller.api.admin;

import ru.reksoft.market.data.dto.CategoryDto;
import ru.reksoft.market.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/dictionary/categories")
public class CategoryAdminController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryAdminController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * POST method for creation the category
     *
     * @param categoryDto - category that should be saved
     * @return saved category
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@Validated @RequestBody CategoryDto categoryDto) {
        return categoryService.createCategory(categoryDto);
    }

    /**
     * PUT method for editing the category
     *
     * @param id          - id of the category that should be updated
     * @param categoryDto - new category fields
     * @return updated category
     */
    @PutMapping("/{id}")
    public CategoryDto updateCategory(@PathVariable Long id, @Validated @RequestBody CategoryDto categoryDto) {
        return categoryService.updateCategory(id, categoryDto);
    }

    /**
     * DELETE method for the category
     *
     * @param id - id of the category that should be deleted
     * @return deleted category
     */
    @DeleteMapping("/{id}")
    public CategoryDto deleteCategory(@PathVariable Long id) {
        return categoryService.deleteCategory(id);
    }
}
