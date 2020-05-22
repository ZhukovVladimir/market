package com.example.market.services;

import com.example.market.data.dto.ImageDto;
import com.example.market.data.models.Image;
import com.example.market.data.repositories.ImageRepository;
import com.example.market.exceptions.BadRequestException;
import com.example.market.exceptions.InternalServerErrorException;
import com.example.market.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    private final ModelMapper modelMapper;

    @Value("${image.upload.dir}")
    private String rootDir;

    @Autowired
    public ImageService(ImageRepository imageRepository, ModelMapper modelMapper) {
        this.imageRepository = imageRepository;
        this.modelMapper = modelMapper;
    }

    private ImageDto findOne(Long id) {
        return modelMapper.map(
                imageRepository.findById(id)
                        .orElseThrow(ResourceNotFoundException::new),
                ImageDto.class);
    }

    public ImageDto saveImage(MultipartFile image) {
        if (image.isEmpty()) {
            throw new BadRequestException("Image not selected");
        }
        ImageDto imageDto;
        try {
            byte[] bytes = image.getBytes();
            Path path = Paths.get(rootDir + image.getOriginalFilename());
            Image imageEntity = new Image()
                    .setName(image.getOriginalFilename());
            Files.write(path, bytes);
            imageDto = modelMapper.map(
                    imageRepository.save(imageEntity),
                    ImageDto.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new InternalServerErrorException("IOException into server");
        }
        return imageDto;

    }

    public byte[] getImageById(Long id) {
        byte[] bytes;
        ImageDto image = findOne(id);

        try (FileInputStream in = new FileInputStream(rootDir + image.getName())) {
            bytes = IOUtils.toByteArray(in);
        } catch (IOException e) {
            e.printStackTrace();
            throw new InternalServerErrorException("IOException into server");
        }
        return bytes;
    }
}
