package ru.homework.cdrtest.component;

import org.springframework.stereotype.Component;
import ru.homework.cdrtest.entity.PhoneNumber;
import ru.homework.cdrtest.repository.CallRecordRepository;
import ru.homework.cdrtest.repository.PhoneNumberRepository;

import java.util.*;

@Component
public class BillingRealTime {
    PhoneNumberRepository phoneNumberRepository;
    CallRecordRepository callRecordRepository;
    HighPerformanceRatingServer highPerfomanceRatingServer;
    CallDataRecord callDataRecord;

    public BillingRealTime(PhoneNumberRepository phoneNumberRepository, CallRecordRepository callRecordRepository,
                           HighPerformanceRatingServer highPerfomanceRatingServer, CallDataRecord callDataRecord) {
        this.phoneNumberRepository = phoneNumberRepository;
        this.callRecordRepository = callRecordRepository;
        this.highPerfomanceRatingServer = highPerfomanceRatingServer;
        this.callDataRecord = callDataRecord;
    }

    public Map<String, Object> billing(){
        //Вызываем метод, который генерирует звонки
        callDataRecord.generateCDR();
        // Получаем список номеров телефонов с балансом больше 0
        List<PhoneNumber> phoneNumbers = phoneNumberRepository.findAllByBalanceGreaterThan(0);
        // Создаем Map, которая будет содержать ответ сервера
        Map<String, Object> responseBody = new LinkedHashMap<>();
        List<Map<String,Object>> numbers = new ArrayList<>();
        // Итерируемся по каждому номеру телефона
        for (PhoneNumber phoneNumber: phoneNumbers) {
            Map<String, Object> data = new LinkedHashMap<>();
            // Для каждого номера телефона нам нужен только итоговая стоимость звонков за месяц
            double totalCost = (double) highPerfomanceRatingServer.calculate(phoneNumber).get(0).get("totalCost");
            // Изменяем баланс номера телефона
            phoneNumber.setBalance(phoneNumber.getBalance()-totalCost);
            // Обновляем сущность в базе данных
            phoneNumberRepository.save(phoneNumber);
            // Добавляем данные о номере телефона в список numbers
            data.put("phoneNumber", phoneNumber.getPhoneNumber());
            data.put("balance", phoneNumber.getBalance());
            numbers.add(data);
        }
        // Добавляем список номеров телефонов и их баланс в словарь
        responseBody.put("numbers", numbers);
        // Возвращаем словарь
        return responseBody;
    }

}
