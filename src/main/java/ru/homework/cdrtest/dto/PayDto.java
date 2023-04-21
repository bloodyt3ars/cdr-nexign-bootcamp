package ru.homework.cdrtest.dto;

import lombok.Data;
import ru.homework.cdrtest.entity.PhoneNumber;

@Data
public class PayDto {
    private PhoneNumber numberPhone;
    private int money;
}
