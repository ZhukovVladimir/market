package ru.reksoft.market.controller.api.admin;

import ru.reksoft.market.data.dto.ModelDto;
import ru.reksoft.market.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/dictionary/models")
public class ModelAdminController {

    private final ModelService modelService;

    @Autowired
    public ModelAdminController(ModelService modelService) {
        this.modelService = modelService;
    }

    /**
     * POST method for creation the model
     *
     * @param modelDto - model that should be saved
     * @return saved model
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ModelDto createModel(@Validated @RequestBody ModelDto modelDto) {
        return modelService.createModel(modelDto);
    }

    /**
     * PUT method for editing the model
     *
     * @param id       - id of the model that should be updated
     * @param modelDto - new model fields
     * @return updated model
     */
    @PutMapping("/{id}")
    public ModelDto updateModel(@PathVariable Long id, @Validated @RequestBody ModelDto modelDto) {
        return modelService.updateModel(id, modelDto);
    }

    /**
     * DELETE method for the model
     *
     * @param id - id of the model that should be deleted
     * @return deleted model
     */
    @DeleteMapping("/{id}")
    public ModelDto deleteModel(@PathVariable Long id) {
        return modelService.deleteModel(id);
    }

}
