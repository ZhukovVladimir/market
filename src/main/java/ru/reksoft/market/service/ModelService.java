package ru.reksoft.market.service;

import org.springframework.security.access.prepost.PreAuthorize;
import ru.reksoft.market.data.dto.ModelDto;
import ru.reksoft.market.data.model.Model;
import ru.reksoft.market.data.repository.ModelRepository;
import ru.reksoft.market.exception.BadRequestException;
import ru.reksoft.market.exception.ResourceNotFoundException;
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelDto createModel(ModelDto modelDto) {
        if (modelDto.getId() != null) {
            throw new BadRequestException("Id should be null");
        } else {
            Model model = modelMapper.map(modelDto, Model.class);
            model = modelRepository.save(model);
            return modelMapper.map(model, ModelDto.class);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelDto updateModel(Long id, ModelDto modelDto) {
        if (modelRepository.existsById(id)) {
            Model model = modelMapper.map(modelDto, Model.class).setId(id);
            model = modelRepository.save(model);
            return modelMapper.map(model, ModelDto.class);
        }
        throw new ResourceNotFoundException(id, "Model");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelDto deleteModel(Long id) {
        Model model = modelRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        model = modelRepository.save(model.setDeleted(true));
        return modelMapper.map(model, ModelDto.class);
    }
}
