package ru.practicum.shareit.request.controller;

import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.util.List;

@RestController
@RequestMapping("/requests")
public class ItemRequestController {
    private final ItemRequestService requestService;

    public ItemRequestController(ItemRequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping
    public ItemRequestDto createRequest(@RequestBody ItemRequestDto requestDto) {
        return requestService.createRequest(requestDto);
    }

    @GetMapping
    public List<ItemRequestDto> getAllRequests() {
        return requestService.getAllRequests();
    }

    @GetMapping("/{id}")
    public ItemRequestDto getRequestById(@PathVariable Long id) {
        return requestService.getRequestById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteRequest(@PathVariable Long id) {
        requestService.deleteRequest(id);
    }
}
