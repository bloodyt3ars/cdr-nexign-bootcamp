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

    //возвращает список записей звонков для заданного телефонного номера.
    List<CallRecord> findAllByPhoneNumber(PhoneNumber phoneNumber);

    /*
    метод использует @Query для создания запроса, который выбирает все записи звонков для заданного месяца и телефонного номера.
    Здесь EXTRACT(MONTH FROM cr.callStart) извлекает месяц из поля callStart, чтобы сравнить его с переданным месяцем.
    :month и :phoneNumber являются параметрами запроса, которые передаются через аннотацию @Param.
     */
    @Query("SELECT cr FROM CallRecord cr WHERE EXTRACT(MONTH FROM cr.callStart) = :month AND cr.phoneNumber = :phoneNumber")
    List<CallRecord> findAllByMonthAndPhoneNumber(@Param("month") int month, @Param("phoneNumber") PhoneNumber phoneNumber);

    //возвращает список записей звонков для заданного телефонного номера.
    List<CallRecord> findAllByReceivingPhoneNumber(PhoneNumber phoneNumber);
}
