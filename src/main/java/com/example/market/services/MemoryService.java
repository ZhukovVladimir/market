package com.example.market.services;

import com.example.market.data.dto.CategoryDto;
import com.example.market.data.dto.MemoryDto;
import com.example.market.data.models.Category;
import com.example.market.data.models.Memory;
import com.example.market.data.repositories.MemoryRepository;
import com.example.market.exceptions.ConflictException;
import com.example.market.exceptions.ResourceNotFoundException;
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

    public MemoryDto createMemory(MemoryDto memoryDto) {
        if (memoryDto.getId() != null && memoryRepository.existsById(memoryDto.getId())) {
            throw new ConflictException("Memory is already exist");
        } else {
            Memory memory = modelMapper.map(memoryDto, Memory.class);
            memory = memoryRepository.save(memory);
            return modelMapper.map(memory, MemoryDto.class);
        }
    }

    public MemoryDto updateMemory(Long id, MemoryDto memoryDto) {
        if (memoryRepository.existsById(id)) {
            Memory memory = modelMapper.map(memoryDto, Memory.class).setId(id);
            memory = memoryRepository.save(memory);
            return modelMapper.map(memory, MemoryDto.class);
        }
        throw new ResourceNotFoundException(id, "Memory");
    }

    public MemoryDto deleteMemory(Long id) {
        Memory memory = memoryRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        memory = memoryRepository.save(memory.setDeleted(true));
        return modelMapper.map(memory, MemoryDto.class);
    }
}
