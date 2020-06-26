package ru.reksoft.market.service;

import org.springframework.security.access.prepost.PreAuthorize;
import ru.reksoft.market.data.dto.CategoryDto;
import ru.reksoft.market.data.model.Category;
import ru.reksoft.market.data.repository.CategoryRepository;
import ru.reksoft.market.exception.BadRequestException;
import ru.reksoft.market.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    public List<CategoryDto> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CategoryDto createCategory(CategoryDto categoryDto) {
        if (categoryDto.getId() != null) {
            throw new BadRequestException("Id should be null");
        } else {
            Category category = modelMapper.map(categoryDto, Category.class);
            category = categoryRepository.save(category);
            return modelMapper.map(category, CategoryDto.class);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        if (categoryRepository.existsById(id)) {
            Category category = modelMapper.map(categoryDto, Category.class).setId(id);
            category = categoryRepository.save(category);
            return modelMapper.map(category, CategoryDto.class);
        }
        throw new ResourceNotFoundException(id, "Category");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CategoryDto deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        category = categoryRepository.save(category.setDeleted(true));
        return modelMapper.map(category, CategoryDto.class);
    }
}
