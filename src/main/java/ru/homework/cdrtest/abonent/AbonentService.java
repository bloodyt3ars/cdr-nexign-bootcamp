package ru.homework.cdrtest.abonent;

import jakarta.transaction.Transactional;
import ru.homework.cdrtest.billingrealtime.Action;
import ru.homework.cdrtest.callrecord.CallRecordReportDTO;

import java.util.List;

public interface AbonentService {

    AbonentDTO save(AbonentCreateDTO dto) throws Exception;

    AbonentDTO pay(AbonentPayDTO dto);

    CallRecordReportDTO report(String phoneNumber) throws IllegalAccessException;

    AbonentDTO changeTariffType(AbonentTariffDTO dto) throws IllegalAccessException;

    List<AbonentShortDTO> billing(Action action) throws IllegalAccessException;
}
