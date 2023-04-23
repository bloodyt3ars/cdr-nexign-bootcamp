package ru.homework.cdrtest.swagger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import ru.homework.cdrtest.component.HighPerformanceRatingServer;
import ru.homework.cdrtest.entity.CallRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportResponse {
    @JsonProperty("id")
    @Schema(example = "235235")
    private String id;

    @JsonProperty("numberPhone")
    @Schema(example = "7123123123")
    private String numberPhone;

    @JsonProperty("tariffIndex")
    @Schema(example = "03")
    private String tariffIndex;

    @JsonProperty("payload")
    private List<CallRecordResponse> payload;

    @JsonProperty("totalCost")
    @Schema(example = "5.00")
    private String totalCost;

    @JsonProperty("monetaryUnit")
    @Schema(example = "rubles")
    private String monetaryUnit;
}
