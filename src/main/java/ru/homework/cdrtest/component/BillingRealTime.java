package ru.homework.cdrtest.component;

import org.springframework.stereotype.Component;
import ru.homework.cdrtest.entity.PhoneNumber;
import ru.homework.cdrtest.repository.CallRecordRepository;
import ru.homework.cdrtest.repository.PhoneNumberRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BillingRealTime {
    PhoneNumberRepository phoneNumberRepository;
    CallRecordRepository callRecordRepository;
    HighPerfomanceRatingServer highPerfomanceRatingServer;
    CallDataRecord callDataRecord;

    public BillingRealTime(PhoneNumberRepository phoneNumberRepository, CallRecordRepository callRecordRepository,
                           HighPerfomanceRatingServer highPerfomanceRatingServer, CallDataRecord callDataRecord) {
        this.phoneNumberRepository = phoneNumberRepository;
        this.callRecordRepository = callRecordRepository;
        this.highPerfomanceRatingServer = highPerfomanceRatingServer;
        this.callDataRecord = callDataRecord;
    }

    public Map<String, Object> billing(){
        callDataRecord.generateCDR();
        List<PhoneNumber> phoneNumbers = phoneNumberRepository.findAllByBalanceGreaterThan(0);
        Map<String, Object> responseBody = new HashMap<>();
        List<Map<String,Object>> numbers = new ArrayList<>();
        for (PhoneNumber phoneNumber: phoneNumbers) {
            Map<String, Object> data = new HashMap<>();
            double totalCost = (double) highPerfomanceRatingServer.calculate(phoneNumber).get(0).get("totalCost");
            phoneNumber.setBalance(phoneNumber.getBalance()-totalCost);
            phoneNumberRepository.save(phoneNumber);
            data.put("phoneNumber", phoneNumber.getPhoneNumber());
            data.put("balance", phoneNumber.getBalance());
            numbers.add(data);
        }
        responseBody.put("numbers", numbers);
        return responseBody;
    }

}
