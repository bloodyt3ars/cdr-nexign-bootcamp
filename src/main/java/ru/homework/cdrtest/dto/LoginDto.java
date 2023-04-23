package ru.homework.cdrtest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Авторизация")
public class LoginDto {
    @Schema(description = "Логин", example = "i.petrov")
    private String username;
    @Schema(description = "Пароль", example = "i.P*tr*v")
    private String password;
}
