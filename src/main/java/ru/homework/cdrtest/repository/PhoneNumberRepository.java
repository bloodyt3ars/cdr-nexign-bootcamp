package ru.homework.cdrtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.homework.cdrtest.entity.PhoneNumber;

import java.util.List;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {
    List<PhoneNumber> findAllByBalanceGreaterThan(int i);
    PhoneNumber findPhoneNumberByPhoneNumber(String numberPhone);
}
