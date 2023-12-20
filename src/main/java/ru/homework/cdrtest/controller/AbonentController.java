package ru.homework.cdrtest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.homework.cdrtest.abonent.AbonentDTO;
import ru.homework.cdrtest.abonent.AbonentPayDTO;
import ru.homework.cdrtest.abonent.AbonentService;
import ru.homework.cdrtest.callrecord.CallRecordReportDTO;



@Tag(name = "abonent",
        description = "В этом разделе находятся методы взаимодействия абонента с системой")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/abonent")
public class AbonentController {
    private final AbonentService service;

    @PatchMapping("/pay")
    @Operation(summary = "Пополнение баланса абонентом", responses = {
            @ApiResponse(responseCode = "200", description = "Информация о абоненте", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос"),
            @ApiResponse(responseCode = "404", description = "Абонент не найден"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AbonentDTO.class))})
    })
    public ResponseEntity<AbonentDTO> pay(@Valid @RequestBody AbonentPayDTO dto) {
        return ResponseEntity.ok(service.pay(dto));
    }

    @PatchMapping("/report/{numberPhone}")
    @Operation(summary = "Получение отчета", responses = {
            @ApiResponse(responseCode = "200", description = "Детализация звонков", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос"),
            @ApiResponse(responseCode = "404", description = "Абонент не найден"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CallRecordReportDTO.class))})
    })
    public ResponseEntity<CallRecordReportDTO> pay(@PathVariable("numberPhone") @Parameter(description = "Номер телефона") String numberPhone) throws IllegalAccessException {
        return ResponseEntity.ok(service.report(numberPhone));
    }
}
