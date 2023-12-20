package ru.homework.cdrtest.highperformancerating;

import jakarta.validation.constraints.NotNull;
import ru.homework.cdrtest.abonent.AbonentEntity;
import ru.homework.cdrtest.callrecord.CallType;

import java.util.List;

public interface HighPerformanceRatingService {

    Double calculate(AbonentEntity abonent, CallType callType, Integer callDuration);
}
