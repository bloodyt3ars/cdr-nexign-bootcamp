package ru.homework.cdrtest.dto;

import lombok.Data;
import ru.homework.cdrtest.entity.Abonent;
import ru.homework.cdrtest.entity.PhoneNumber;

@Data
public class AbonentDto {
    private String username;
    private String numberPhone;
    private String tariff_id;
    private int balance;
}
