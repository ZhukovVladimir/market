package com.example.market.controller.api;

import com.example.market.data.dto.ModelDto;
import com.example.market.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dictionary/models")
public class ModelController {

    private final ModelService modelService;

    @Autowired
    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    /**
     * GET method for model list
     *
     * @return list with all models
     */
    @GetMapping
    public List<ModelDto> getAllModels() {
        return modelService.findAll();
    }
}
