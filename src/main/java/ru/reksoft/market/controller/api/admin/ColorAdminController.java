package ru.reksoft.market.controller.api.admin;

import ru.reksoft.market.data.dto.ColorDto;
import ru.reksoft.market.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/dictionary/colors")
public class ColorAdminController {

    private final ColorService colorService;

    @Autowired
    public ColorAdminController(ColorService colorService) {
        this.colorService = colorService;
    }

    /**
     * POST method for creation the color
     * @param colorDto - color that should be saved
     * @return saved color
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ColorDto createColor(@Validated @RequestBody ColorDto colorDto) {
        return colorService.createColor(colorDto);
    }

    /**
     * PUT method for editing the color
     * @param id - id of the color that should be updated
     * @param colorDto - new color fields
     * @return updated color
     */
    @PutMapping("/{id}")
    public ColorDto updateColor(@PathVariable Long id, @Validated @RequestBody ColorDto colorDto) {
        return colorService.updateColor(id, colorDto);
    }

    /**
     * DELETE method for the color
     * @param id - id of the color that should be deleted
     * @return deleted color
     */
    @DeleteMapping("/{id}")
    public ColorDto deleteColor(@PathVariable Long id) {
        return colorService.deleteColor(id);
    }
}
