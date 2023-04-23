package ru.homework.cdrtest.swagger;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Data;
import ru.homework.cdrtest.entity.CallType;

import java.time.LocalDateTime;

@Schema
@Data
public class CallRecordResponse {
    @JsonProperty("callType")
    @Schema(example = "01")
    private CallType callType; // тип вызова (01 - исходящие, 02 - входящие)
    @JsonProperty("callStart")
    @Schema(example = "2023-02-03 05:55:06")
    private LocalDateTime callStart; // дата и время начала звонка
    @JsonProperty("callEnd")
    @Schema(example = "2023-02-03 06:02:49")
    private LocalDateTime callEnd; // дата и время окончания звонка
    @JsonProperty("duration")
    @Schema(example = "00:07:43")
    private String duration;
    @JsonProperty("cost")
    @Schema(example = "5.00")
    private String cost;
}
