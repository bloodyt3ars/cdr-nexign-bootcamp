package ru.homework.cdrtest.callrecord;

import java.io.File;
import java.io.IOException;

public interface CallRecordService {
    void readCDR(File file) throws IOException;
}
