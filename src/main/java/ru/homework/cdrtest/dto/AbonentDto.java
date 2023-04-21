package ru.homework.cdrtest.dto;

import lombok.Data;
import ru.homework.cdrtest.entity.Abonent;
import ru.homework.cdrtest.entity.PhoneNumber;

@Data
public class AbonentDto {
    private Abonent abonent;
    private PhoneNumber numberPhone;
    private String tariff_id;
    private int balance;
}
