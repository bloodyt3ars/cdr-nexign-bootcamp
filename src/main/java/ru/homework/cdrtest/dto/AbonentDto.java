package ru.homework.cdrtest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.homework.cdrtest.entity.Abonent;
import ru.homework.cdrtest.entity.PhoneNumber;

@Data
@Schema(description = "Сущность пользователя")
public class AbonentDto {
    @Schema(description = "Логин", example = "i.petrov")
    private String username;
    @Schema(description = "Номер телефона", example = "78005553535")
    private String numberPhone;
    @Schema(description = "Тариф", example = "11")
    private String tariff_id;
    @Schema(description = "Баланс", example = "400")
    private int balance;
}
