package com.example.market.data.services;

import com.example.market.data.dto.MemoryDto;
import com.example.market.data.models.Memory;
import com.example.market.data.repositories.MemoryRepository;
import com.example.market.exceptions.ForbiddenException;
import org.apache.catalina.mapper.Mapper;
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

    public MemoryDto saveMemory(MemoryDto memoryDto) {
        if (memoryDto.getId() != null && memoryRepository.existsById(memoryDto.getId())) {
            throw new ForbiddenException("Memory is already exist");
        } else {
            Memory memory = modelMapper.map(memoryDto, Memory.class);
            memory = memoryRepository.save(memory);
            return modelMapper.map(memory, MemoryDto.class);
        }
    }
}
