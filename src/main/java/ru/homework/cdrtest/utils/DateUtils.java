package ru.homework.cdrtest.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils {
    public static Instant getCurrentTime(){
        return Instant.now();
    }
}
