package ru.homework.cdrtest.callrecord;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.homework.cdrtest.abonent.AbonentEntity;
import ru.homework.cdrtest.abonent.AbonentRepository;

import java.io.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CallDataRecordServiceImpl implements CallDataRecordService {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private final AbonentRepository abonentRepository;

    @Override
    public List<CallRecordEntity> readFile(File file) throws IOException {
        List<CallRecordEntity> callRecords = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line = null;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                String[] fields = line.split(",");
                String phoneNumber = fields[1].trim();
                AbonentEntity abonent = getAbonentByPhoneNumber(phoneNumber);
                CallType callType = fields[0].trim().equals("01") ? CallType.OUTGOING : CallType.INCOMING;
                LocalDateTime dateTimeCallStart = LocalDateTime.parse(fields[2].trim(), DATE_TIME_FORMATTER);
                LocalDateTime dateTimeCallEnd = LocalDateTime.parse(fields[3].trim(), DATE_TIME_FORMATTER);
                Instant callStart = dateTimeCallStart.atZone(ZoneId.systemDefault()).toInstant();
                Instant callEnd = dateTimeCallEnd.atZone(ZoneId.systemDefault()).toInstant();

                CallRecordEntity callRecord = new CallRecordEntity()
                        .setAbonent(abonent)
                        .setCallType(callType)
                        .setCallStart(callStart)
                        .setCallEnd(callEnd);
                callRecords.add(callRecord);
            }
        } catch (Exception e) {
            log.error("Ошибка при чтении строки {}", line, e);
        }
        bufferedReader.close();
        return callRecords;
    }

    private AbonentEntity getAbonentByPhoneNumber(String phoneNumber) {
        return abonentRepository.findAbonentByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new EntityNotFoundException("Абонент не найден"));
    }
}
