package ru.homework.cdrtest.abonent;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.homework.cdrtest.tarifftype.TariffTypeDTO;


@Data
@Schema(description = "Модель пользователя")
public class AbonentDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Идентификатор пользователя")
    private Long id;

    @Schema(description = "Номер телефона")
    private String phoneNumber;

    @Schema(description = "Баланс")
    private Double balance;

    @Schema(description = "Тип тарифа")
    private TariffTypeDTO tariffType;
}
