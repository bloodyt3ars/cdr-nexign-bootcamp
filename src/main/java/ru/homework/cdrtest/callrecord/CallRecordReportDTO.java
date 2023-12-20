package ru.homework.cdrtest.callrecord;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.homework.cdrtest.tarifftype.TariffType;

import java.util.List;

@Data
@Accessors(chain = true)
@Schema(description = "Модель детализации звонков")
public class CallRecordReportDTO {
    @Schema(description = "Идентификатор пользователя")
    private Long id;

    @Schema(description = "Номер телефона")
    private String phoneNumber;

    @Schema(description = "Тип тарифа")
    private TariffType tariffType;

    @JsonProperty("payload")
    @Schema(description = "Список звонков")
    private List<CallRecordDTO> callRecords;

    @Schema(description = "Общая стоймость звонков")
    private Double totalCost;

    @Schema(description = "Валюта")
    private String monetaryUnit;
}
