package ru.homework.cdrtest.abonent;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Краткая модель пользователя")
public class AbonentShortDTO {
    @Schema(description = "Номер телефона")
    private String phoneNumber;

    @Schema(description = "Баланс")
    private Double balance;
}
