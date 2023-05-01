package ru.homework.cdrtest.component;

import org.springframework.stereotype.Component;
import ru.homework.cdrtest.entity.Abonent;
import ru.homework.cdrtest.entity.PhoneNumber;
import ru.homework.cdrtest.entity.TariffType;
import ru.homework.cdrtest.repository.AbonentRepository;
import ru.homework.cdrtest.repository.PhoneNumberRepository;

import java.util.Random;

@Component
public class GeneratePhoneNumbers {

    private static final int PHONE_NUMBER_LENGTH = 11;
    private static final Random random = new Random();
    private final PhoneNumberRepository phoneNumberRepository;

    private final AbonentRepository abonentRepository;

    public GeneratePhoneNumbers(PhoneNumberRepository phoneNumberRepository, AbonentRepository abonentRepository) {
        this.phoneNumberRepository = phoneNumberRepository;
        this.abonentRepository = abonentRepository;
    }
    /*
    Этот код генерирует заданное количество телефонных номеров с рандомным балансом
    и тарифным планом для указанного абонента.
     */
    public void generate(int n) {//Аргумент "n" задает количество номеров, которые будут сгенерированы.
        for (int i = 0; i < n; i++) {
            //Генерируется случайный телефонный номер с помощью метода generatePhoneNumber().
            String generatePhoneNumber = generatePhoneNumber();
            //Генерируется случайный баланс с помощью класса Random.
            double balance = Math.ceil(random.nextDouble() * 1000.0 * 100) / 100;
            //Получается абонент с помощью метода findById() из репозитория abonentRepository с заданным идентификатором "abonent_id".
            long abonent_id = 1;
            Abonent byId = abonentRepository.findById(abonent_id).orElse(null);
            //Генерируется случайный тип тарифного плана из перечисления TariffType.
            int tariff_type_id = random.nextInt(TariffType.values().length);
            TariffType tariffType = TariffType.values()[tariff_type_id];
            //Создается новый объект PhoneNumber и устанавливаются ему свойства: номер телефона, баланс, абонент и тип тарифного плана.
            PhoneNumber phoneNumber = new PhoneNumber();
            phoneNumber.setPhoneNumber(generatePhoneNumber);
            phoneNumber.setBalance(balance);
            phoneNumber.setAbonent(byId);
            phoneNumber.setTariffType(tariffType);
            //Созданный объект PhoneNumber сохраняется в репозитории phoneNumberRepository с помощью метода save().
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
