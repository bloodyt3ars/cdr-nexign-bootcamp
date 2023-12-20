package ru.homework.cdrtest.billingrealtime;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.homework.cdrtest.abonent.AbonentEntity;
import ru.homework.cdrtest.callrecord.CallRecordRepository;
import ru.homework.cdrtest.callrecord.CallType;
import ru.homework.cdrtest.highperformancerating.HighPerformanceRatingService;
import ru.homework.cdrtest.tarifftype.TariffType;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static java.time.Duration.between;
import static ru.homework.cdrtest.utils.DateUtils.getCurrentTime;

@Service
@RequiredArgsConstructor
public class BillingRealTimeServiceImpl implements BillingRealTimeService {
    private final CallRecordRepository callRecordRepository;
    private final HighPerformanceRatingService highPerformanceRatingService;

    @Override
    public void performBilling(List<AbonentEntity> abonents) {
        abonents.forEach(abonent -> {
            AtomicReference<Double> totalCost = new AtomicReference<>(0.00);
            var callRecords = callRecordRepository.findAllByPhoneNumberCurrentMonthNotCalculate(abonent.getPhoneNumber(), getCurrentTime());
            callRecords.forEach(callRecord -> {
                Duration duration = between(callRecord.getCallStart(), callRecord.getCallEnd());
                Integer callDuration = getCallDurationMinutes(duration);
                Double cost = highPerformanceRatingService.calculate(abonent, callRecord.getCallType(), callDuration);
                setFreeMinutes(abonent, callRecord.getCallType(), callDuration);
                callRecord.setDuration(duration);
                callRecord.setCost(cost);
                totalCost.updateAndGet(v -> v + cost);
            });
            abonent.setBalance(abonent.getBalance()-totalCost.get());
        });
    }

    private void setFreeMinutes(AbonentEntity abonent, CallType callType, Integer callDuration) {
        if (Objects.requireNonNull(abonent.getTariffType().getTariffType()) == TariffType.NORMAL) {
            if (callType == CallType.OUTGOING) {
                abonent.setFreeMinutes(abonent.getFreeMinutes() - callDuration);
            }
        } else if (Objects.requireNonNull(abonent.getTariffType().getTariffType()) == TariffType.UNLIMITED_300) {
            abonent.setFreeMinutes(abonent.getFreeMinutes() - callDuration);
        }
    }

    private Integer getCallDurationMinutes(Duration duration) {
        if (duration.getSeconds() / 1 % 60 == 0.0) {
            return Math.toIntExact(duration.getSeconds() / 60);
        } else {
            return (Math.toIntExact(duration.getSeconds() / 60)) + 1;
        }
    }
}


