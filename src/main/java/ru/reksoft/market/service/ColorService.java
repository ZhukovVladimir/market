package ru.reksoft.market.service;

import org.springframework.security.access.prepost.PreAuthorize;
import ru.reksoft.market.data.dto.ColorDto;
import ru.reksoft.market.data.model.Color;
import ru.reksoft.market.data.repository.ColorRepository;
import ru.reksoft.market.exception.BadRequestException;
import ru.reksoft.market.exception.ResourceNotFoundException;
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ColorDto createColor(ColorDto colorDto) {
        if (colorDto.getId() != null) {
            throw new BadRequestException("Id should be null");
        } else {
            Color color = modelMapper.map(colorDto, Color.class);
            color = colorRepository.save(color);
            return modelMapper.map(color, ColorDto.class);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ColorDto updateColor(Long id, ColorDto colorDto) {
        if (colorRepository.existsById(id)) {
            Color color = modelMapper.map(colorDto, Color.class).setId(id);
            color = colorRepository.save(color);
            return modelMapper.map(color, ColorDto.class);
        }
        throw new ResourceNotFoundException(id, "Color");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ColorDto deleteColor(Long id) {
        Color color = colorRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        color = colorRepository.save(color.setDeleted(true));
        return modelMapper.map(color, ColorDto.class);
    }
}
