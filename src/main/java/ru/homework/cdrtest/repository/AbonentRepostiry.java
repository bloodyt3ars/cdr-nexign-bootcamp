package ru.homework.cdrtest.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.homework.cdrtest.entity.Abonent;


@Repository
public interface AbonentRepostiry extends JpaRepository<Abonent, Long> {
}
