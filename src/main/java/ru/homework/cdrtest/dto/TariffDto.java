package ru.homework.cdrtest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Изменить тариф")
public class TariffDto {
    @Schema(description = "Номер телефона", example = "78005553535")
    String numberPhone;
    @Schema(description = "Желаемый тариф", example = "06")
    String tariff_id;
}
