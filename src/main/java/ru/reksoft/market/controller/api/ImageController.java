package ru.reksoft.market.controller.api;

import ru.reksoft.market.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller for working with images
 */
@Controller
@RequestMapping(value = "/api/images")
public class ImageController {

    private final ImageService imageService;

    /**
     * Constructor with all fields
     *
     * @param imageService - autowired
     */
    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    /**
     * GET method for the image
     *
     * @param id - id of the image you want to get
     * @return image as byte array
     */
    @GetMapping("/{id}")
    @ResponseBody
    public byte[] getImage(@PathVariable Long id) {
        return imageService.getImageById(id);
    }

}
