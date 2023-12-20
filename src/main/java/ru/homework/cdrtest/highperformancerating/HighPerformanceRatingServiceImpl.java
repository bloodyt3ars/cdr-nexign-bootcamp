package ru.homework.cdrtest.highperformancerating;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.homework.cdrtest.abonent.AbonentEntity;
import ru.homework.cdrtest.callrecord.CallType;
import ru.homework.cdrtest.tarifftype.TariffType;
import ru.homework.cdrtest.tarifftype.TariffTypeEntity;
import ru.homework.cdrtest.tarifftype.TariffTypeRepository;

@Service
@RequiredArgsConstructor
public class HighPerformanceRatingServiceImpl implements HighPerformanceRatingService {
    private final TariffTypeRepository tariffTypeRepository;

    @Override
    public Double calculate(AbonentEntity abonent, CallType callType, Integer callDuration) {
        TariffTypeEntity tariffType = abonent.getTariffType();
        Integer freeMinutes = abonent.getFreeMinutes();
        switch (tariffType.getTariffType()) {
            case NORMAL -> {
                if (callType == CallType.INCOMING) {
                    return 0.00;
                } else if (callType == CallType.OUTGOING) {
                    return calculateNormalCost(tariffType, freeMinutes, callDuration);
                }
            }
            case PER_MINUTE -> {
                return calculatePerMinuteCost(tariffType, callDuration);
            }
            case UNLIMITED_300 -> {
                return calculateUnlimited300Cost(tariffType, freeMinutes, callDuration);
            }
        }
        return null;
    }

    private Double calculateNormalCost(TariffTypeEntity tariffType, Integer freeMinutes, Integer duration) {
        double cost;
        if ((freeMinutes >= 0) && (duration < freeMinutes)) {
            cost = duration * tariffType.getMinutePrice();
        } else if ((freeMinutes >= 0) && (duration > freeMinutes)) {
            cost = freeMinutes * tariffType.getMinutePrice() + calculatePerMinuteCost(getTariffType(TariffType.PER_MINUTE), duration - freeMinutes);
        } else {
            cost = calculatePerMinuteCost(getTariffType(TariffType.PER_MINUTE), duration);
        }
        return cost;
    }

    private double calculateUnlimited300Cost(TariffTypeEntity tariffType, Integer freeMinutes, Integer duration) {
        double cost;
        if ((freeMinutes >= 0) && (duration < freeMinutes)) {
            cost = 0;
        } else {
            if ((freeMinutes >= 0) && (duration > freeMinutes)) {
                cost = (duration - freeMinutes) * tariffType.getMinutePrice();
            } else {
                cost = duration * tariffType.getMinutePrice();
            }
        }
        return cost;
    }

    private double calculatePerMinuteCost(TariffTypeEntity tariffType, Integer duration) {
        return duration * tariffType.getMinutePrice();
    }

    private TariffTypeEntity getTariffType(TariffType type) {
        return tariffTypeRepository.findByTariffType(type)
                .orElseThrow(() ->
                        new EntityNotFoundException("Тариф не найден"));
    }
}
