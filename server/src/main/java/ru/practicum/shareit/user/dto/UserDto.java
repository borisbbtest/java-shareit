package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    @NotNull
    @NotBlank(message = "Имя не может быть пустым")
    private String name;
    @NotNull
    @NotBlank(message = "Логин не может быть пустым")
    @Email(message = "Некорректный формат email")
    private String email;
}
