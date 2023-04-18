package ru.homework.cdrtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.homework.cdrtest.component.GeneratePhoneNumbers;
import ru.homework.cdrtest.repository.CallRecordRepository;
import ru.homework.cdrtest.repository.PhoneNumberBalanceHistoryRepository;
import ru.homework.cdrtest.repository.PhoneNumberRepository;

@SpringBootApplication
public class CdrTestApplication {

    public static void main(String[] args) {

		ConfigurableApplicationContext run = SpringApplication.run(CdrTestApplication.class, args);

		CallRecordRepository callRecordRepository = run.getBean(CallRecordRepository.class);
		PhoneNumberBalanceHistoryRepository phoneNumberBalanceHistoryRepository = run.getBean(PhoneNumberBalanceHistoryRepository.class);
		PhoneNumberRepository phoneNumberRepository = run.getBean(PhoneNumberRepository.class);

		callRecordRepository.deleteAll();
		phoneNumberBalanceHistoryRepository.deleteAll();
		phoneNumberRepository.deleteAll();

		GeneratePhoneNumbers generatePhoneNumbers = run.getBean(GeneratePhoneNumbers.class);
		generatePhoneNumbers.generate(10);

    }

}
