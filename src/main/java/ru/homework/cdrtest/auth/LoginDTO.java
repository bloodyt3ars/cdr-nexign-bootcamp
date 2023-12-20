package ru.homework.cdrtest.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@Schema(description = "Модель авторизации")
public class LoginDTO {
    @NotNull
    @Schema(description = "Номер телефона")
    private String phoneNumber;

    @NotNull
    @Schema(description = "Пароль")
    private String password;
}