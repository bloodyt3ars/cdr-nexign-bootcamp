package ru.homework.cdrtest.component;

import org.springframework.stereotype.Component;
import ru.homework.cdrtest.entity.Abonent;
import ru.homework.cdrtest.entity.PhoneNumber;
import ru.homework.cdrtest.entity.TariffType;
import ru.homework.cdrtest.repository.AbonentRepostiry;
import ru.homework.cdrtest.repository.PhoneNumberRepository;

import java.util.Random;

@Component
public class GeneratePhoneNumbers {

    private static final int PHONE_NUMBER_LENGTH = 11;
    private static final Random random = new Random();
    private final PhoneNumberRepository phoneNumberRepository;

    private final AbonentRepostiry abonentRepostiry;

    public GeneratePhoneNumbers(PhoneNumberRepository phoneNumberRepository, AbonentRepostiry abonentRepostiry) {
        this.phoneNumberRepository = phoneNumberRepository;
        this.abonentRepostiry = abonentRepostiry;
    }

    public void generate(int n) {

        for (int i = 0; i < n; i++) {
            String generatePhoneNumber = generatePhoneNumber();
            double balance = Math.ceil(random.nextDouble(1000) * 100) / 100;
            long abonent_id = 1;
            Abonent byId = abonentRepostiry.findById(abonent_id).orElse(null);
            int tariff_type_id = random.nextInt(TariffType.values().length);
            TariffType tariffType = TariffType.values()[tariff_type_id];
            PhoneNumber phoneNumber = new PhoneNumber();
            phoneNumber.setPhoneNumber(generatePhoneNumber);
            phoneNumber.setBalance(balance);
            phoneNumber.setAbonent(byId);
            phoneNumber.setTariffType(tariffType);
            phoneNumberRepository.save(phoneNumber);
        }

    }

    private static String generatePhoneNumber() {
        StringBuilder sb = new StringBuilder();
        sb.append("7");             // код страны
        sb.append("9");             // первая цифра номера
        sb.append(getRandomDigits(PHONE_NUMBER_LENGTH - 2));  // оставшиеся цифры
        return sb.toString();
    }

    private static int getRandomDigit() {
        return random.nextInt(10);
    }

    private static String getRandomDigits(int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(getRandomDigit());
        }
        return sb.toString();
    }
}
