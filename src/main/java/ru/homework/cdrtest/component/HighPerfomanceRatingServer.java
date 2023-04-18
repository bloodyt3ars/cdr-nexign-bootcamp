package ru.homework.cdrtest.component;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ru.homework.cdrtest.entity.CallRecord;
import ru.homework.cdrtest.entity.CallType;
import ru.homework.cdrtest.entity.PhoneNumber;
import ru.homework.cdrtest.entity.TariffType;
import ru.homework.cdrtest.repository.CallRecordRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class HighPerfomanceRatingServer {

    private final CallRecordRepository callRecordRepository;

    public HighPerfomanceRatingServer(CallRecordRepository callRecordRepository) {
        this.callRecordRepository = callRecordRepository;
    }

    public List<Map> calculate(@NotNull PhoneNumber phoneNumber) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("id", phoneNumber.getId());
        responseBody.put("numberPhone", phoneNumber.getPhoneNumber());
        responseBody.put("tariff", phoneNumber.getTariffType().getName());
        List<Map> payload = new ArrayList<>();
        TariffType tariffType = phoneNumber.getTariffType();
        List<CallRecord> callRecords = callRecordRepository.findAllByMonthAndPhoneNumber(CallDataRecord.getMonth(), phoneNumber);
        double totalCost = tariffType.getFixedPrice();
        int minutesRemain = tariffType.getFreeMinutes();
        for (CallRecord callRecord : callRecords) {
            Map<String, Object> map = new LinkedHashMap<>();
            String callType = callRecord.getCallType().getName();
            String startTime = formatDateTime(callRecord.getCallStart());
            String callEnd = formatDateTime(callRecord.getCallEnd());
            String duration = getCallDurationForPrint(callRecord.getCallStart(), callRecord.getCallEnd());
            double cost = 0;
            int dur = getCallDurationMinutes(callRecord.getCallStart(), callRecord.getCallEnd());
            if (tariffType == TariffType.NORMAL) {
                if (callRecord.getCallType() == CallType.INCOMING) {
                    cost = 0.00;
                } else if (callRecord.getCallType() == CallType.OUTGOING) {
                    cost = calculateNormalCost(minutesRemain, dur);
                    minutesRemain -= getCallDurationMinutes(callRecord.getCallStart(), callRecord.getCallEnd());
                }
            } else if (tariffType == TariffType.UNLIMITED_300) {
                cost = calculateUnlimited300Cost(minutesRemain, dur);
                minutesRemain -= getCallDurationMinutes(callRecord.getCallStart(), callRecord.getCallEnd());
            } else if (tariffType == TariffType.PER_MINUTE) {
                cost = calculatePerMinuteCost(dur);
            } else {
                throw new IllegalArgumentException("Unknown tariff type");
            }
            map.put("callType", callType);
            map.put("startTime", startTime);
            map.put("callEnd", callEnd);
            map.put("duration", duration);
            map.put("cost", cost);
            payload.add(map);
            totalCost += cost;
        }
        responseBody.put("payload", payload);
        responseBody.put("totalCost", totalCost);
        responseBody.put("monetaryUnit", "rubles");

        List<Map> returnList = new ArrayList<>();
        returnList.add(responseBody);
        return returnList;
    }

    private String getCallDurationForPrint(LocalDateTime callStart, LocalDateTime callEnd) {
        Duration duration = Duration.between(callStart, callEnd);
        long second = duration.getSeconds();
        long hour = second / 3600;
        long min = second / 60 % 60;
        long sec = second / 1 % 60;
        return String.format("%02d:%02d:%02d", hour, min, sec);
    }

    private int getCallDurationMinutes(LocalDateTime callStart, LocalDateTime callEnd) {
        Duration duration = Duration.between(callStart, callEnd);
        if (duration.getSeconds() / 1 % 60 == 0.0) {
            return (int) duration.getSeconds() / 60;
        } else {
            return ((int) duration.getSeconds() / 60) + 1;
        }
    }

    private double calculateUnlimited300Cost(int freeMinutes, int duration) {
        double cost;
        if ((freeMinutes >= 0) && (duration < freeMinutes)) {
            cost = 0;
        } else {
            if ((freeMinutes >= 0) && (duration > freeMinutes)) {
                cost = (duration - freeMinutes) * 1.0;
            } else {
                cost = duration * 1.0;
            }
        }
        return cost;
    }

    private double calculatePerMinuteCost(int duration) {
        return duration * 1.5;
    }

    private double calculateNormalCost(int freeMinutes, int duration) {
        double cost;
        if ((freeMinutes >= 0) && (duration < freeMinutes)) {
            cost = duration * 0.5;
        } else if ((freeMinutes >= 0) && (duration > freeMinutes)) {
            cost = freeMinutes * 0.5 + (duration - freeMinutes) * 1.5;
        } else {
            cost = duration * 1.5;
        }
        return cost;
    }

    private static String formatDateTime(LocalDateTime dt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss0");
        return dt.format(formatter);
    }
}
