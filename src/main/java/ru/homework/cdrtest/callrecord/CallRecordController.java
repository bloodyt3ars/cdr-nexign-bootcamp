package ru.homework.cdrtest.callrecord;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@Tag(name = "cdr",
        description = "В этом разделе можно загрузить записи в систему")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/auth")
public class CallRecordController {
    private final CallRecordService service;
    @PostMapping("/read-cdr")
    @Operation(summary = "Загрузка записей в систему", responses = {
            @ApiResponse(responseCode = "200", description = "Записи загружены", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка",
                    content = {@Content(mediaType = MediaType.TEXT_EVENT_STREAM_VALUE, schema = @Schema(implementation = String.class))})
    })
    public ResponseEntity<String> save(@Valid @RequestBody File file) throws Exception {
        service.readCDR(file);
        return ResponseEntity.ok("Успех");
    }
}
