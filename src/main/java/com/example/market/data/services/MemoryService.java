package com.example.market.data.services;

import com.example.market.data.repositories.MemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemoryService {

    private final MemoryRepository memoryRepository;

    @Autowired
    public MemoryService(MemoryRepository memoryRepository) {
        this.memoryRepository = memoryRepository;
    }
}
