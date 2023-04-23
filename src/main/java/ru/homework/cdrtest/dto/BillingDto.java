package ru.homework.cdrtest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Тарификация")
public class BillingDto {
    @Schema(description = "Действие", example = "run")
    private String action;
}
