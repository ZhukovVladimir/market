package com.example.market.controller.api;

import com.example.market.data.dto.ColorDto;
import com.example.market.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dictionary/colors")
public class ColorController {

    private final ColorService colorService;

    @Autowired
    public ColorController(ColorService colorService) {
        this.colorService = colorService;
    }

    /**
     * GET method for color list
     *
     * @return list with all colors
     */
    @GetMapping
    public List<ColorDto> getAllColors() {
        return colorService.findAll();
    }
}
