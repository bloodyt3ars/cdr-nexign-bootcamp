package ru.homework.cdrtest.tarifftype;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Schema(description = "Модель типа тарифов")
public class TariffTypeDTO {
    @Schema(description = "Идентификатор")
    private Long id;

    @Schema(description = "Код тарифа")
    private String code;

    @Schema(description = "Наименование тарифа")
    private String name;

    @Schema(description = "Стоймость за минуту")
    private Double minutePrice;

    @Schema(description = "Кол-во песплатных минут")
    private Long freeMinutes;

    @Schema(description = "Фиксированная стоймость за тариф")
    private Double fixedPrice;

    @Schema(description = "Тип тарифа")
    private TariffType tariffType;
}
