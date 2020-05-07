package com.example.market.data.services;

import com.example.market.data.dto.ModelDto;
import com.example.market.data.models.Model;
import com.example.market.data.repositories.ModelRepository;
import com.example.market.exceptions.ForbiddenException;
import org.dom4j.rule.Mode;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModelService {

    private final ModelRepository modelRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ModelService(ModelRepository modelRepository, ModelMapper modelMapper) {
        this.modelRepository = modelRepository;
        this.modelMapper = modelMapper;
    }

    public List<ModelDto> findAll() {
        return modelRepository.findAll()
                .stream()
                .map(model -> modelMapper.map(model, ModelDto.class))
                .collect(Collectors.toList());
    }

    public ModelDto saveModel(ModelDto modelDto) {
        if (modelDto.getId() != null && modelRepository.existsById(modelDto.getId())) {
            throw new ForbiddenException("Model is already exist");
        } else {
            Model model = modelMapper.map(modelDto, Model.class);
            model = modelRepository.save(model);
            return modelMapper.map(model, ModelDto.class);
        }
    }
}
