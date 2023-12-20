package ru.homework.cdrtest.callrecord;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
@Hidden
public interface CallRecordRepository extends JpaRepository<CallRecordEntity, Long> {
    @Query("""
            SELECT cr FROM CallRecordEntity cr where cr.abonent.phoneNumber = :phoneNumber and
            function('year', cr.callStart) = function('year', :now) and
            function('MONTH', cr.callStart) = function('MONTH', :now) and
            cr.cost <> null and cr.duration <> null
            
            """)
    List<CallRecordEntity> findAllByPhoneNumberCurrentMonth(@NotNull String phoneNumber,
                                                            @NotNull Instant now);

    @Query("""
            SELECT cr FROM CallRecordEntity cr where cr.abonent.phoneNumber = :phoneNumber and
            function('year', cr.callStart) = function('year', :now) and
            function('MONTH', cr.callStart) = function('MONTH', :now) and
            cr.cost = null and cr.duration = null
            """)
    List<CallRecordEntity> findAllByPhoneNumberCurrentMonthNotCalculate(@NotNull String phoneNumber,
                                                            @NotNull Instant now);
}
