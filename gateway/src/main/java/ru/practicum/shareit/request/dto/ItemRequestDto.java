package ru.practicum.shareit.request.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemRequestDto {
    private Long id;

    @NotBlank(message = "Описание запроса не может быть пустым")
    private String description;

    private LocalDateTime created;

    private List<ItemResponseDto> items;
}
