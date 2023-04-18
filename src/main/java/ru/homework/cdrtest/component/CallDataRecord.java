package ru.homework.cdrtest.component;

import org.springframework.stereotype.Component;
import ru.homework.cdrtest.entity.CallRecord;
import ru.homework.cdrtest.entity.CallType;
import ru.homework.cdrtest.entity.PhoneNumber;
import ru.homework.cdrtest.repository.CallRecordRepository;
import ru.homework.cdrtest.repository.PhoneNumberRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
public class CallDataRecord {
    private final PhoneNumberRepository phoneNumberRepository;
    private final CallRecordRepository callRecordRepository;



    private static int month = 1;

    public CallDataRecord(PhoneNumberRepository phoneNumberRepository, CallRecordRepository callRecordRepository) {
        this.phoneNumberRepository = phoneNumberRepository;
        this.callRecordRepository = callRecordRepository;
    }


    public void generateCDR(){
        List<CallRecord> callRecords = new ArrayList<>();
        List<PhoneNumber> phoneNumbers = phoneNumberRepository.findAllByBalanceGreaterThan(0);
        for (PhoneNumber phoneNumber: phoneNumbers) {
            Random random = new Random();
            int n = random.nextInt(30);
            for (int i = 0; i < n; i++) {
                CallRecord callRecord = new CallRecord();
                CallType callType = getRandomCallType();
                LocalDateTime startTime = getRandomDateTime();
                LocalDateTime endTime = startTime.plusMinutes(getRandomDuration());
                callRecord.setPhoneNumber(phoneNumber);
                callRecord.setCallType(callType);
                callRecord.setCallStart(startTime);
                callRecord.setCallEnd(endTime);
                callRecords.add(callRecord);
            }
        }
        monthSetup();
        Collections.sort(callRecords);
        callRecordRepository.saveAll(callRecords);
    }


    private CallType getRandomCallType() {
        Random random = new Random();
        return random.nextBoolean() ? CallType.INCOMING : CallType.OUTGOING;
    }
    private static int getRandomDuration() {//можно поправить, что бы были минуты и секунды
        Random random = new Random();
        return random.nextInt(60) + 1; // звонок длится от 1 до 60 минут
    }
    private LocalDateTime getRandomDateTime() {//Надо подправить, чтобы небыло пересекающихся звонков у одного абонента
        Random random = new Random();
        int year = 2022;
        int month = CallDataRecord.month;
        int day = random.nextInt(28) + 1;
        int hour = random.nextInt(24);
        int minute = random.nextInt(60);
        int second = random.nextInt(60);
        return LocalDateTime.of(year, month, day, hour, minute, second);
    }
    private static void monthSetup(){
        month++;
        if (month > 12){
            month=month-12;
        }
    }
    public static int getMonth() {
        return month-1;
    }


}
