package ru.reksoft.market.service;

import org.springframework.security.access.prepost.PreAuthorize;
import ru.reksoft.market.data.dto.MemoryDto;
import ru.reksoft.market.data.model.Memory;
import ru.reksoft.market.data.repository.MemoryRepository;
import ru.reksoft.market.exception.BadRequestException;
import ru.reksoft.market.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemoryService {

    private final MemoryRepository memoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MemoryService(MemoryRepository memoryRepository, ModelMapper modelMapper) {
        this.memoryRepository = memoryRepository;
        this.modelMapper = modelMapper;
    }

    public List<MemoryDto> findAll() {
        return memoryRepository.findAll()
                .stream()
                .map(memory -> modelMapper.map(memory, MemoryDto.class))
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public MemoryDto createMemory(MemoryDto memoryDto) {
        if (memoryDto.getId() != null) {
            throw new BadRequestException("Id should be null");
        } else {
            Memory memory = modelMapper.map(memoryDto, Memory.class);
            memory = memoryRepository.save(memory);
            return modelMapper.map(memory, MemoryDto.class);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public MemoryDto updateMemory(Long id, MemoryDto memoryDto) {
        if (memoryRepository.existsById(id)) {
            Memory memory = modelMapper.map(memoryDto, Memory.class).setId(id);
            memory = memoryRepository.save(memory);
            return modelMapper.map(memory, MemoryDto.class);
        }
        throw new ResourceNotFoundException(id, "Memory");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public MemoryDto deleteMemory(Long id) {
        Memory memory = memoryRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        memory = memoryRepository.save(memory.setDeleted(true));
        return modelMapper.map(memory, MemoryDto.class);
    }
}
