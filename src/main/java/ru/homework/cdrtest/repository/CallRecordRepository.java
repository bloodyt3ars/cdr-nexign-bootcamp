package ru.homework.cdrtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.homework.cdrtest.entity.CallRecord;
import ru.homework.cdrtest.entity.PhoneNumber;

import java.util.List;

@Repository
public interface CallRecordRepository extends JpaRepository<CallRecord, Long> {

    List<CallRecord> findAllByPhoneNumber(PhoneNumber phoneNumber);
    @Query("SELECT cr FROM CallRecord cr WHERE EXTRACT(MONTH FROM cr.callStart) = :month AND cr.phoneNumber = :phoneNumber")
    List<CallRecord> findAllByMonthAndPhoneNumber(@Param("month") int month, @Param("phoneNumber") PhoneNumber phoneNumber);
}
