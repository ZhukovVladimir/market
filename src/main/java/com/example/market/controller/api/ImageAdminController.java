package com.example.market.controller.api;

import com.example.market.data.dto.ImageDto;
import com.example.market.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/api/admin/images")
public class ImageAdminController {

    private final ImageService imageService;

    //path to storage directory
    private String dirRoot;

    /**
     * Constructor with all fields
     *
     * @param imageService - autowired
     * @param dirRoot      - setting up in the property file (image.upload.dir = value)
     */
    @Autowired
    public ImageAdminController(ImageService imageService,
                           @Value("${image.upload.dir}") String dirRoot) {
        this.imageService = imageService;
        this.dirRoot = dirRoot;
    }

    /**
     * POST method for saving the image
     *
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
