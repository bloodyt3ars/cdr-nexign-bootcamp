package ru.homework.cdrtest.callrecord;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.Duration;
import java.time.Instant;

@Data
@Schema(description = "Модель звонка")
public class CallRecordDTO {
    @Schema(description = "Тип вызова")
    private CallType callType;

    @Schema(description = "Дата начала звонка")
    private Instant callStart;

    @Schema(description = "Дата окончания звонка")
    private Instant callEnd;

    @Schema(name = "Длительность звонка")
    private Duration duration;

    @Schema(description = "Стоймость")
    private Double cost;
}
