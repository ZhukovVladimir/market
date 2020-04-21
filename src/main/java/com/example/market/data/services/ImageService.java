package com.example.market.data.services;

import com.example.market.data.dto.ImageDto;
import com.example.market.data.repositories.ImageRepository;
import com.example.market.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public ImageService(ImageRepository imageRepository, ModelMapper modelMapper) {
        this.imageRepository = imageRepository;
        this.modelMapper = modelMapper;
    }

    public ImageDto findOne(Long id) {
        return modelMapper.map(
                imageRepository.findById(id)
                        .orElseThrow(ResourceNotFoundException::new),
                ImageDto.class);
    }
}
