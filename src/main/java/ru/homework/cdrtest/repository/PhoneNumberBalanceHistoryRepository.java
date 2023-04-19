package ru.homework.cdrtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.homework.cdrtest.entity.PhoneNumber;
import ru.homework.cdrtest.entity.PhoneNumberBalanceHistory;

import java.util.List;

public interface PhoneNumberBalanceHistoryRepository extends JpaRepository<PhoneNumberBalanceHistory, Long> {

    //позволяет получить все записи из таблицы для заданного телефонного номера
    List<PhoneNumberBalanceHistory> findAllByPhoneNumber(PhoneNumber phoneNumber);
}
