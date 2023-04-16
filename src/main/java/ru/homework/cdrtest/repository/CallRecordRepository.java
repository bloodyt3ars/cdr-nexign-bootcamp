package ru.homework.cdrtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.homework.cdrtest.entity.CallRecord;
import org.springframework.data.repository.CrudRepository;
import ru.homework.cdrtest.entity.PhoneNumber;

import java.util.List;

@Repository
public interface CallRecordRepository extends JpaRepository<CallRecord, Long> {

    List<CallRecord> findAllByPhoneNumber(PhoneNumber phoneNumber);
}
