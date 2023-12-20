package ru.homework.cdrtest.callrecord;

import ru.homework.cdrtest.callrecord.CallRecordEntity;

import java.io.File;
import java.io.IOException;
import java.util.List;


public interface CallDataRecordService {
    List<CallRecordEntity> readFile(File file) throws IOException;
}
