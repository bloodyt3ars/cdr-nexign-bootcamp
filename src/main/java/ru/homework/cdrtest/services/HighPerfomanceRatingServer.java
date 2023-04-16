package ru.homework.cdrtest.services;

import org.springframework.stereotype.Component;
import ru.homework.cdrtest.entity.CallRecord;
import ru.homework.cdrtest.repository.PhoneNumberRepository;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HighPerfomanceRatingServer {


    private PhoneNumberRepository phoneNumberRepository;

    public HighPerfomanceRatingServer(PhoneNumberRepository phoneNumberRepository) {
        this.phoneNumberRepository = phoneNumberRepository;
    }

    public void calculate(List<CallRecord> callRecords){
        Map<String,String> map = new HashMap<>();
        List<Map> payload = new ArrayList<>();
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

    private int getCallDurationMinutes() {
        Duration duration = Duration.between(callStart, callEnd);
        if (duration.getSeconds() / 1 % 60 == 0.0){
            return (int) duration.getSeconds() / 60;
        }
        else {
            return ((int) duration.getSeconds() / 60)+1;
        }
    }
}
