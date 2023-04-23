package ru.homework.cdrtest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Для оплаты")
public class PayDto {
    @Schema(description = "Номер телефона", example = "78005553535")
    private String numberPhone;
    @Schema(description = "Деньги", example = "400")
    private int money;
}
