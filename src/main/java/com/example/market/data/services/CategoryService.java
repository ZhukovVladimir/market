package com.example.market.data.services;

import com.example.market.data.dto.CategoryDto;
import com.example.market.data.models.Category;
import com.example.market.data.repositories.CategoryRepository;
import com.example.market.exceptions.ForbiddenException;
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

    public CategoryDto saveCategory(CategoryDto categoryDto) {
        if (categoryDto.getId() != null && categoryRepository.existsById(categoryDto.getId())){
            throw new ForbiddenException("Category is already exist");
        } else {
            Category category = modelMapper.map(categoryDto, Category.class);
            category = categoryRepository.save(category);
            return modelMapper.map(category, CategoryDto.class);
        }
    }
}
