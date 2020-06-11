package ru.reksoft.market.controller.api.admin;

import ru.reksoft.market.data.dto.ImageDto;
import ru.reksoft.market.service.ImageService;
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

    /**
     * Constructor with all fields
     *
     * @param imageService - autowired
     */
    @Autowired
    public ImageAdminController(ImageService imageService) {
        this.imageService = imageService;
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
