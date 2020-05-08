package com.example.market.services;

import com.example.market.data.dto.CategoryDto;
import com.example.market.data.dto.ColorDto;
import com.example.market.data.models.Category;
import com.example.market.data.models.Color;
import com.example.market.data.repositories.ColorRepository;
import com.example.market.exceptions.ConflictException;
import com.example.market.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ColorService {

    private final ColorRepository colorRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ColorService(ColorRepository colorRepository, ModelMapper modelMapper) {
        this.colorRepository = colorRepository;
        this.modelMapper = modelMapper;
    }

    public List<ColorDto> findAll() {
        return colorRepository.findAll()
                .stream()
                .map(color -> modelMapper.map(color, ColorDto.class))
                .collect(Collectors.toList());
    }

    public ColorDto createColor(ColorDto colorDto) {
        if (colorDto.getId() != null && colorRepository.existsById(colorDto.getId())){
            throw new ConflictException("Color is already exist");
        } else {
            Color color = modelMapper.map(colorDto, Color.class);
            color = colorRepository.save(color);
            return modelMapper.map(color, ColorDto.class);
        }
    }

    public ColorDto updateColor(Long id, ColorDto colorDto) {
        if (colorRepository.existsById(id)) {
            Color color = modelMapper.map(colorDto, Color.class).setId(id);
            color = colorRepository.save(color);
            return modelMapper.map(color, ColorDto.class);
        }
        throw new ResourceNotFoundException(id, "Color");
    }

    public ColorDto deleteColor(Long id) {
        Color color = colorRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        color = colorRepository.save(color.setDeleted(true));
        return modelMapper.map(color, ColorDto.class);
    }
}
