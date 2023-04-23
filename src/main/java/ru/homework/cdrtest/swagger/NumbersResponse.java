package ru.homework.cdrtest.swagger;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public class NumbersResponse {
    @JsonProperty("phoneNumber")
    @Schema(example = "7123123123")
    private String phoneNumber;

    @JsonProperty("balance")
    @Schema(example = "440")
    private String balance;
}
