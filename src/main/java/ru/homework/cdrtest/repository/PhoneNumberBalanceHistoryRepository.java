package ru.homework.cdrtest.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.homework.cdrtest.entity.PhoneNumber;
import ru.homework.cdrtest.entity.PhoneNumberBalanceHistory;

import java.util.List;


@Repository
@Hidden
public interface PhoneNumberBalanceHistoryRepository extends JpaRepository<PhoneNumberBalanceHistory, Long> {

    //позволяет получить все записи из таблицы для заданного телефонного номера
    List<PhoneNumberBalanceHistory> findAllByPhoneNumber(PhoneNumber phoneNumber);
}
