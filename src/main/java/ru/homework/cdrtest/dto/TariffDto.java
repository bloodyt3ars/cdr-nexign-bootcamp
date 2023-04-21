package ru.homework.cdrtest.dto;

import lombok.Data;
import ru.homework.cdrtest.entity.PhoneNumber;

@Data
public class TariffDto {
    PhoneNumber numberPhone;
    String tariff_id;
}
