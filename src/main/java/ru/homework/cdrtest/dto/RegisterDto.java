package ru.homework.cdrtest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Регистрация пользователя")
public class RegisterDto {
    @Schema(description = "Имя", example = "Иван")
    private String firstName;
    @Schema(description = "Фамилия", example = "Петров")
    private String lastName;
    @Schema(description = "Логин", example = "i.petrov")
    private String username;
    @Schema(description = "Пароль", example = "i.P*tr*v")
    private String password;
}
