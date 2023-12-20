package ru.homework.cdrtest.abonent;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Модель данных для смены тарифа")
public class AbonentTariffDTO {
    @Schema(description = "Номер телефона")
    private String numberPhone;

    @Schema(description = "Тип тарифа")
    private Long tariffTypeId;
}