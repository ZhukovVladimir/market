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

/**
 * Controller for working with images
 */
@Controller
@PropertySource("classpath:application.properties")
@RequestMapping(value = "/api/image")
public class ImageController {

    private final ImageService imageService;

    //path to storage directory
    private String dirRoot;

    /**
     * Constructor with all fields
     * @param imageService - autowired
     * @param dirRoot - setting up in the property file (image.upload.dir = value)
     */
    @Autowired
    public ImageController(ImageService imageService,
                           @Value("${image.upload.dir}") String dirRoot) {
        this.imageService = imageService;
        this.dirRoot = dirRoot;
    }

    /**
     * GET method for the image
     * @param id - id of the image you want to get
     * @return image as byte array
     */
    @GetMapping("/{id}")
    @ResponseBody
    public byte[] getImage(@PathVariable Long id) {
        return imageService.getImageById(id);
    }

    /**
     * POST method for saving the image
     * @param image - image as multipart/form-data should be saved
     * @return ImageDto with id and name of the saved image
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ImageDto saveImage(@RequestParam("image") MultipartFile image) {
        return imageService.saveImage(image);
    }
}
