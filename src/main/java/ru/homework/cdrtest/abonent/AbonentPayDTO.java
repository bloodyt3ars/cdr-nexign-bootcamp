package ru.homework.cdrtest.abonent;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Модель данных для пополнения баланса")
public class AbonentPayDTO {
    @Schema(description = "Номер телефона")
    private String numberPhone;

    @Schema(description = "Деньги")
    private Double money;
}
