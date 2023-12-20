package ru.homework.cdrtest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.homework.cdrtest.abonent.*;
import ru.homework.cdrtest.billingrealtime.Action;

import java.util.List;



@Tag(name = "manager",
        description = "В этом разделе находятся методы взаимодействия менеджера с системой")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/manager")
public class ManagerController {
    private final AbonentService service;

    @PatchMapping("/changeTariff")
    @Operation(summary = "Изменение тарифа менеджером", responses = {
            @ApiResponse(responseCode = "200", description = "Информация о абоненте", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос"),
            @ApiResponse(responseCode = "404", description = "Абонент не найден"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AbonentDTO.class))})
    })
    public ResponseEntity<AbonentDTO> pay(@Valid @RequestBody AbonentTariffDTO dto) throws IllegalAccessException {
        return ResponseEntity.ok(service.changeTariffType(dto));
    }

    @PostMapping("/abonent")
    @Operation(summary = "Создание нового абонента", responses = {
            @ApiResponse(responseCode = "200", description = "Детализация звонков", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AbonentDTO.class))})
    })
    public ResponseEntity<AbonentDTO> save(@Valid @RequestBody AbonentCreateDTO dto) throws Exception {
        return ResponseEntity.ok(service.save(dto));
    }

    @PatchMapping("/billing")
    @Operation(summary = "Тарификация абонентов менеджером", responses = {
            @ApiResponse(responseCode = "200", description = "Информация о абонентах", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AbonentShortDTO.class))})
    })
    public ResponseEntity<List<AbonentShortDTO>> billing(@Valid @RequestBody Action action) throws IllegalAccessException {
        return ResponseEntity.ok(service.billing(action));
    }
}
