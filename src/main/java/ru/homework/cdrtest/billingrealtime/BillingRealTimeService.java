package ru.homework.cdrtest.billingrealtime;

import ru.homework.cdrtest.abonent.AbonentEntity;

import java.util.List;

public interface BillingRealTimeService {
    void performBilling(List<AbonentEntity> abonents);
}
