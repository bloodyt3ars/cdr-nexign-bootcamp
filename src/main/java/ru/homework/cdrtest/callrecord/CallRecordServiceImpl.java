package ru.homework.cdrtest.callrecord;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CallRecordServiceImpl implements CallRecordService {
    private final CallDataRecordService service;
    private final CallRecordRepository repository;
    @Override
    public void readCDR(File file) throws IOException {
        repository.saveAll(service.readFile(file));
    }
}
