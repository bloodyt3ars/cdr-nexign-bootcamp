package ru.homework.cdrtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.homework.cdrtest.entity.PhoneNumber;

import java.util.List;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {
    //метод для поиска всех номеров телефонов, у которых баланс больше указанного значения.
    List<PhoneNumber> findAllByBalanceGreaterThan(double balance);
    //метод для поиска номера телефона по его номеру.
    PhoneNumber findPhoneNumberByPhoneNumber(String numberPhone);
}
