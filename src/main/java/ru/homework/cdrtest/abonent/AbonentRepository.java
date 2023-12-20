package ru.homework.cdrtest.abonent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AbonentRepository extends JpaRepository<AbonentEntity, Long> {
    @Query("select a from AbonentEntity a where a.balance > 0")
    List<AbonentEntity> findAllWithPositiveBalance();
    @Query("select a from AbonentEntity a where a.phoneNumber = :phoneNumber")
    Optional<AbonentEntity> findAbonentByPhoneNumber(String phoneNumber);
}
