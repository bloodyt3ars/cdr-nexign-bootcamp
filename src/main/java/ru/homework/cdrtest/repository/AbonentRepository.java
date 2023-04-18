package ru.homework.cdrtest.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.homework.cdrtest.entity.Abonent;


@Repository
public interface AbonentRepository extends JpaRepository<Abonent, Long> {
}
