package com.example.market.controllers.api;

import com.example.market.data.dto.ImageDto;
import com.example.market.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@PropertySource("classpath:application.properties")
@RequestMapping(value = "/api/image")
public class ImageController {

    private final ImageService imageService;

    private String dirRoot;

    @Autowired
    public ImageController(ImageService imageService,
                           @Value("${image.upload.dir}") String dirRoot) {
        this.imageService = imageService;
        this.dirRoot = dirRoot;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public byte[] getImage(@PathVariable Long id) {
        return imageService.getImageById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ImageDto saveImage(@RequestParam("image") MultipartFile image) {
        return imageService.saveImage(image);
    }
}
