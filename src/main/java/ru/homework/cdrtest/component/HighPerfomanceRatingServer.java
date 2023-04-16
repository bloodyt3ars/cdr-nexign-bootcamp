package ru.homework.cdrtest.component;

import org.springframework.stereotype.Component;
import ru.homework.cdrtest.entity.CallRecord;
import ru.homework.cdrtest.entity.CallType;
import ru.homework.cdrtest.entity.PhoneNumber;
import ru.homework.cdrtest.entity.TariffType;
import ru.homework.cdrtest.repository.CallRecordRepository;
import ru.homework.cdrtest.repository.PhoneNumberRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HighPerfomanceRatingServer {


    private PhoneNumberRepository phoneNumberRepository;

    private CallRecordRepository callRecordRepository;

    public HighPerfomanceRatingServer(PhoneNumberRepository phoneNumberRepository, CallRecordRepository callRecordRepository) {
        this.phoneNumberRepository = phoneNumberRepository;
        this.callRecordRepository = callRecordRepository;
    }

    public void calculate(List<PhoneNumber> phoneNumbers){
        List<Map> payload = new ArrayList<>();
        for (PhoneNumber phoneNumber: phoneNumbers) {
            TariffType tariffType = phoneNumber.getTariffType();
            List<CallRecord> callRecords = callRecordRepository.findAllByPhoneNumber(phoneNumber);
            for (CallRecord callRecord:callRecords) {
                Map<String, String> map = new HashMap<>();
                String callType = callRecord.getCallType().getName();
                String startTime = formatDateTime(callRecord.getCallStart());
                String callEnd = formatDateTime(callRecord.getCallEnd());
                String duration = getCallDurationForPrint(callRecord.getCallStart(), callRecord.getCallEnd());
                double cost =
            }

        }


    }

    private String getCallDurationForPrint(LocalDateTime callStart, LocalDateTime callEnd) {
        Duration duration = Duration.between(callStart, callEnd);
        long second = duration.getSeconds();
        long hour = second / 3600;
        long min = second / 60 % 60;
        long sec = second / 1 % 60;
        return String.format("%02d:%02d:%02d", hour, min, sec);
    }
    private int getCallDurationMinutes(LocalDateTime callStart,LocalDateTime callEnd) {
        Duration duration = Duration.between(callStart, callEnd);
        if (duration.getSeconds() / 1 % 60 == 0.0){
            return (int) duration.getSeconds() / 60;
        }
        else {
            return ((int) duration.getSeconds() / 60)+1;
        }
    }
    private double calculateUnlimited300Cost(int freeMinutes) {
        double cost = 0;
        int duration = getCallDurationMinutes();
        if (freeMinutes >= 0) {
            cost = 0;
        } else {
            if ((freeMinutes >= 0) && (duration > freeMinutes)){
                cost = duration-freeMinutes * 1.0;
            } else {
                cost = duration * 1.0;
            }
        }
        this.cost = cost;
        return cost;
    }

    private double calculatePerMinuteCost() {
        int duration = getCallDurationMinutes();
        this.cost = duration * 1.5;
        return duration * 1.5;
    }

    //тут тоже
    private double calculateNormalCost(int freeMinutes) {
        double cost = 0;
        int duration = getCallDurationMinutes();
        if (callType == CallType.INCOMING) {
            cost = 0;
        } else {
            if ((freeMinutes >= 0) && (duration < freeMinutes)) {
                cost = duration * 0.5;
            } else if ((freeMinutes >= 0) && (duration > freeMinutes)){
                cost = freeMinutes * 0.5 + duration-freeMinutes * 1.5;
            } else {
                cost = duration * 1.5;
            }
        }
        this.cost = cost;
        return cost;
    }


    private static String formatDateTime(LocalDateTime dt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss0");
        return dt.format(formatter);
    }
}
