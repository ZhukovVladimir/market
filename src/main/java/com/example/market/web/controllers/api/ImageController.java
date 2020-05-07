package com.example.market.web.controllers.api;

import com.example.market.data.dto.ImageDto;
import com.example.market.data.services.ImageService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

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
    public @ResponseBody
    byte[] getImage(@PathVariable Long id) throws IOException {

        ImageDto image = imageService.findOne(id);

        FileInputStream in = new FileInputStream(dirRoot + image.getName());
        return IOUtils.toByteArray(in);
    }

    @PostMapping
    public @ResponseBody
    ImageDto saveImage(@RequestParam("image") MultipartFile image) {
        return imageService.saveImage(image);
    }
}
