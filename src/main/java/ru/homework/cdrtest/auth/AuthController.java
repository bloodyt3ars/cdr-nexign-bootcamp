package ru.homework.cdrtest.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "auth",
        description = "В этом разделе находятся методы авторизации и идентификации пользователя")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/auth")
public class AuthController {
    private final AuthService service;

    @PostMapping("/login")
    @Operation(summary = "Авторизация пользователя", responses = {
            @ApiResponse(responseCode = "200", description = "Авторизация прошла успешно", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = String.class))})
    })
    public ResponseEntity<String> login(@Valid @RequestBody LoginDTO dto) {
        service.login(dto);
        return ResponseEntity.ok("Авторизация прошла успешно");
    }

    @PostMapping("/logout")
    @Operation(summary = "Выход пользователя из системы", responses = {
            @ApiResponse(responseCode = "200", description = "Выход из системы прошел успешно", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = String.class))})
    })
    public ResponseEntity<String> logout(HttpServletRequest request) {
        service.logout(request);
        return ResponseEntity.ok("Выход из системы прошел успешно");
    }
}
