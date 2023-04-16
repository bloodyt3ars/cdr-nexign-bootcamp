package ru.homework.cdrtest.component;

import org.springframework.stereotype.Component;
import ru.homework.cdrtest.entity.PhoneNumber;
import ru.homework.cdrtest.repository.CallRecordRepository;
import ru.homework.cdrtest.repository.PhoneNumberRepository;

import java.util.List;

@Component
public class BillingRealTime {
    PhoneNumberRepository phoneNumberRepository;
    CallRecordRepository callRecordRepository;
    HighPerfomanceRatingServer HRS;

    public BillingRealTime(PhoneNumberRepository phoneNumberRepository, CallRecordRepository callRecordRepository, HighPerfomanceRatingServer HRS) {
        this.phoneNumberRepository = phoneNumberRepository;
        this.callRecordRepository = callRecordRepository;
        this.HRS = HRS;
    }

    public void selectUserWithPositiveBalance(){
        List<PhoneNumber> phoneNumbers = phoneNumberRepository.findAllByBalanceGreaterThan(0);
    }
}
