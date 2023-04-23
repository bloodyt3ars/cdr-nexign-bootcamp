package ru.homework.cdrtest.swagger;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class BillingResponse {
    @JsonProperty("numbers")
    List<NumbersResponse> numbersResponses;
}
