package com.example.market.data.services;

import com.example.market.data.dto.ImageDto;
import com.example.market.data.models.Image;
import com.example.market.data.repositories.ImageRepository;
import com.example.market.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
@PropertySource("classpath:application.properties")
public class ImageService {

    private final ImageRepository imageRepository;

    private final ModelMapper modelMapper;

    @Value("${image.upload.dir}")
    private String rootDir;

    @Value("${image.upload.defaultname}")
    private String defaultImageName;
    private final Image defaultImage = new Image().setId(-1L).setName(defaultImageName);

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

    public ImageDto saveImage(MultipartFile image) {
        Image savedImageEntity = defaultImage;
        if (!image.isEmpty()) {
            try {
                byte[] bytes = image.getBytes();
                Path path = Paths.get(rootDir + image.getOriginalFilename());
                Files.write(path, bytes);
                savedImageEntity = imageRepository.save(new Image().setName(image.getOriginalFilename()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return modelMapper.map(savedImageEntity, ImageDto.class);
    }
}
