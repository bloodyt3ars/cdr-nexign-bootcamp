package ru.homework.cdrtest.abonent;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.homework.cdrtest.role.Role;

import java.util.List;
import java.util.Set;

@Data
@Accessors(chain = true)
@Schema(description = "Модель создания пользователя")
public class AbonentCreateDTO {
    @NotNull
    @Schema(description = "Номер телефона")
    private String phoneNumber;

    @NotNull
    @Schema(description = "Баланс")
    private Double balance;

    @NotNull
    @Schema(description = "Тип тарифа")
    private Long tariffTypeId;

    @NotNull
    @Schema(description = "Пароль")
    private String password;

    @NotEmpty
    @Schema(description = "Роль")
    private Set<Role> role;
}
