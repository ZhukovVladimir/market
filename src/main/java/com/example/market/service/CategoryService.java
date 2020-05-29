package com.example.market.service;

import com.example.market.data.dto.CategoryDto;
import com.example.market.data.model.Category;
import com.example.market.data.repository.CategoryRepository;
import com.example.market.exception.BadRequestException;
import com.example.market.exception.ResourceNotFoundException;
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

    public CategoryDto createCategory(CategoryDto categoryDto) {
        if (categoryDto.getId() != null) {
            throw new BadRequestException("Id should be null");
        } else {
            Category category = modelMapper.map(categoryDto, Category.class);
            category = categoryRepository.save(category);
            return modelMapper.map(category, CategoryDto.class);
        }
    }

    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        if (categoryRepository.existsById(id)) {
            Category category = modelMapper.map(categoryDto, Category.class).setId(id);
            category = categoryRepository.save(category);
            return modelMapper.map(category, CategoryDto.class);
        }
        throw new ResourceNotFoundException(id, "Category");
    }

    public CategoryDto deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        category = categoryRepository.save(category.setDeleted(true));
        return modelMapper.map(category, CategoryDto.class);
    }
}
