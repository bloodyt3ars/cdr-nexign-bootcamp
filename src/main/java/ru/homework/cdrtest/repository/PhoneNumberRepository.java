package ru.homework.cdrtest.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.homework.cdrtest.entity.Abonent;
import ru.homework.cdrtest.entity.PhoneNumber;

import java.util.List;

@Repository
@Hidden
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {
    //метод для поиска всех номеров телефонов, у которых баланс больше указанного значения.
    List<PhoneNumber> findAllByBalanceGreaterThan(double balance);
    //метод для поиска номера телефона по его номеру.
    PhoneNumber findPhoneNumberByPhoneNumber(String numberPhone);

    List<PhoneNumber> findAllByAbonent(Abonent abonent);
}
