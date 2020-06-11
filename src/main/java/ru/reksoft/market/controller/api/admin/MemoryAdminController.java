package ru.reksoft.market.controller.api.admin;

import ru.reksoft.market.data.dto.MemoryDto;
import ru.reksoft.market.service.MemoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/dictionary/storage")
public class MemoryAdminController {

    private final MemoryService memoryService;

    @Autowired
    public MemoryAdminController(MemoryService memoryService) {
        this.memoryService = memoryService;
    }

    /**
     * POST method for creation the memory
     *
     * @param memoryDto - memory that should be saved
     * @return saved memory
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MemoryDto createMemory(@Validated @RequestBody MemoryDto memoryDto) {
        return memoryService.createMemory(memoryDto);
    }

    /**
     * PUT method for editing the memory
     *
     * @param id        - id of the memory that should be updated
     * @param memoryDto - new memory fields
     * @return updated memory
     */
    @PutMapping("/{id}")
    public MemoryDto updateMemory(@PathVariable Long id, @Validated @RequestBody MemoryDto memoryDto) {
        return memoryService.updateMemory(id, memoryDto);
    }

    /**
     * DELETE method for the memory
     *
     * @param id - id of the memory that should be deleted
     * @return deleted memory
     */
    @DeleteMapping("/{id}")
    public MemoryDto deleteMemory(@PathVariable Long id) {
        return memoryService.deleteMemory(id);
    }
}
