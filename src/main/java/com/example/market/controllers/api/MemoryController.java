package com.example.market.controllers.api;

import com.example.market.data.dto.MemoryDto;
import com.example.market.services.MemoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dictionary/storage")
public class MemoryController {

    private final MemoryService memoryService;

    @Autowired
    public MemoryController(MemoryService memoryService) {
        this.memoryService = memoryService;
    }

    /**
     * GET method for memory list
     *
     * @return list with all types of memory
     */
    @GetMapping
    public List<MemoryDto> getAllMemoryTypes() {
        return memoryService.findAll();
    }
}
