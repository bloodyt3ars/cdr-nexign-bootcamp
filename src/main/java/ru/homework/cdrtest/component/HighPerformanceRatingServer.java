package ru.homework.cdrtest.component;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.VisibleForTesting;
import org.springframework.stereotype.Component;
import ru.homework.cdrtest.entity.CallRecord;
import ru.homework.cdrtest.entity.CallType;
import ru.homework.cdrtest.entity.PhoneNumber;
import ru.homework.cdrtest.entity.TariffType;
import ru.homework.cdrtest.repository.CallRecordRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class HighPerformanceRatingServer {

    private CallRecordRepository callRecordRepository;

    public HighPerformanceRatingServer() {
    }

    public HighPerformanceRatingServer(CallRecordRepository callRecordRepository) {
        this.callRecordRepository = callRecordRepository;
    }



    /*  Этот метод принимает объект PhoneNumber, извлекает необходимую информацию об этом номере телефона (id, номер телефона и тариф),
        а затем использует эту информацию, чтобы рассчитать стоимость звонков, сделанных в течение месяца для этого номера телефона.
        Метод создает новый LinkedHashMap, который содержит поля id, numberPhone, tariff, payload, totalCost и monetaryUnit.
        Поле payload является списком LinkedHashMap, каждый из которых содержит информацию о каждом звонке,
        сделанном в течение месяца для данного номера телефона.
        Метод проходит по всем записям звонков из репозитория callRecordRepository за указанный месяц и номером телефона,
        извлекает данные о звонке (тип звонка, дату начала и конца звонка, продолжительность и стоимость),
        рассчитывает стоимость звонка в зависимости от тарифа и добавляет информацию о звонке в список payload.
        Наконец, метод рассчитывает общую стоимость всех звонков и добавляет ее в LinkedHashMap в поле totalCost.
        Метод также добавляет в LinkedHashMap единицу валюты "rubles" в поле monetaryUnit и возвращает список,
        содержащий этот LinkedHashMap.*/
    public List<Map> calculate(@NotNull PhoneNumber phoneNumber) {
        // Создаем объект для хранения результата
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("id", phoneNumber.getId());
        responseBody.put("numberPhone", phoneNumber.getPhoneNumber());
        responseBody.put("tariff", phoneNumber.getTariffType().getName());
        // Создаем список для хранения результата каждого звонка
        List<Map> payload = new ArrayList<>();
        // Получаем тарифный план и список звонков за текущий месяц и год для номера телефона
        TariffType tariffType = phoneNumber.getTariffType();
        List<CallRecord> callRecords = callRecordRepository.findAllByMonthAndYearAndPhoneNumber(CallDataRecord.getMonth(),
                CallDataRecord.getYear(), phoneNumber);
        // Инициализируем общую стоимость звонков и количество оставшихся бесплатных минут
        double totalCost = tariffType.getFixedPrice();
        int minutesRemain = tariffType.getFreeMinutes();
        // Обходим список звонков и расчитываем стоимость каждого звонка
        for (CallRecord callRecord : callRecords) {
            // Создаем объект для хранения результата текущего звонка
            Map<String, Object> map = new LinkedHashMap<>();
            // Получаем тип звонка, время начала и окончания звонка, и продолжительность звонка для вывода
            String callType = callRecord.getCallType().getName();
            String startTime = formatDateTime(callRecord.getCallStart());
            String callEnd = formatDateTime(callRecord.getCallEnd());
            String duration = getCallDurationForPrint(callRecord.getCallStart(), callRecord.getCallEnd());
            double cost = 0;
            int dur = getCallDurationMinutes(callRecord.getCallStart(), callRecord.getCallEnd());
            // Расчитываем стоимость звонка в зависимости от тарифного плана
            if (tariffType == TariffType.NORMAL) {
                if (callRecord.getCallType() == CallType.INCOMING) {
                    cost = 0.00;
                } else if (callRecord.getCallType() == CallType.OUTGOING) {
                    cost = calculateNormalCost(minutesRemain, dur);
                    minutesRemain -= getCallDurationMinutes(callRecord.getCallStart(), callRecord.getCallEnd());
                }
            } else if (tariffType == TariffType.UNLIMITED_300) {
                cost = calculateUnlimited300Cost(minutesRemain, dur);
                minutesRemain -= getCallDurationMinutes(callRecord.getCallStart(), callRecord.getCallEnd());
            } else if (tariffType == TariffType.PER_MINUTE) {
                cost = calculatePerMinuteCost(dur);
            } else {
                throw new IllegalArgumentException("Unknown tariff type");
            }
            // Записываем результаты расчета текущего звонка в список
            map.put("callType", callType);
            map.put("startTime", startTime);
            map.put("callEnd", callEnd);
            map.put("duration", duration);
            map.put("cost", cost);
            payload.add(map);
            // Обновляем общую стоимость звонков
            totalCost += cost;
        }
        // Записываем результаты расчета
        responseBody.put("payload", payload);
        responseBody.put("totalCost", totalCost);
        responseBody.put("monetaryUnit", "rubles");

        List<Map> returnList = new ArrayList<>();
        returnList.add(responseBody);
        return returnList;
    }

    // Этот метод вычисляет продолжительность звонка, который отобразится пользователю, между callStart и callEnd в формате чч:мм:сс
    private String getCallDurationForPrint(LocalDateTime callStart, LocalDateTime callEnd) {
        // Для этого мы вычисляем продолжительность звонка в секундах, затем переводим ее в часы, минуты и секунды.
        Duration duration = Duration.between(callStart, callEnd);
        long second = duration.getSeconds();
        long hour = second / 3600;
        long min = second / 60 % 60;
        long sec = second / 1 % 60;
        // Наконец, мы форматируем эти значения в строку с помощью метода String.format() и возвращаем результат.
        return String.format("%02d:%02d:%02d", hour, min, sec);
    }
    // Этот метод используется для расчетов.
    private int getCallDurationMinutes(LocalDateTime callStart, LocalDateTime callEnd) {
        // Вычисляем продолжительность звонка
        Duration duration = Duration.between(callStart, callEnd);
        // Если количество секунд кратно 60, то продолжительность звонка в минутах равна количеству секунд, деленному на 60
        if (duration.getSeconds() / 1 % 60 == 0.0) {
            return (int) duration.getSeconds() / 60;
        } else {
            // Иначе, продолжительность звонка в минутах равна количеству секунд, деленному на 60, округленному в большую сторону
            return ((int) duration.getSeconds() / 60) + 1;
        }
    }

    /*
    Метод calculateUnlimited300Cost используется для расчета стоимости звонков для тарифа "Безлимит 300".
     */

    private double calculateUnlimited300Cost(int freeMinutes, int duration) {
        double cost;
        /*
        В первой части метода определяется, будут ли звонки бесплатными. Если звонок
        длится меньше или равно freeMinutes минутам, то его стоимость равна нулю.
         */
        if ((freeMinutes >= 0) && (duration < freeMinutes)) {
            cost = 0;
        } else {
            /*
            В противном случае происходит расчет стоимости звонка.
            Если клиент превысил лимит бесплатных минут, то стоимость звонка рассчитывается как разность
            между общей продолжительностью звонков и количеством бесплатных минут,
            умноженная на стоимость минуты звонка (1.0).
            Если же клиент не превысил лимит бесплатных минут, то стоимость звонка будет равна продолжительности
            звонка умноженной на стоимость минуты звонка (1.0).
             */
            if ((freeMinutes >= 0) && (duration > freeMinutes)) {
                cost = (duration - freeMinutes) * TariffType.UNLIMITED_300.getMinutePrice();
            } else {
                cost = duration * TariffType.UNLIMITED_300.getMinutePrice();
            }
        }
        return cost;
    }

    /*
    Метод calculatePerMinuteCost используется для расчета стоимости звонков для тарифа "Поминутный".
     */
    private double calculatePerMinuteCost(int duration) {
        return duration * TariffType.PER_MINUTE.getMinutePrice();
    }

    /*
    Метод calculateNormalCost используется для расчета стоимости звонков для тарифа "Нормальный".
     */
    private double calculateNormalCost(int freeMinutes, int duration) {
        double cost;
        /*
        Сначала метод проверяет, не превышает ли длительность звонка количество бесплатных минут.
        Если да, то стоимость звонка равна 0.5 рубля за минуту (цена за минуту в рамках бесплатных минут).
         */
        if ((freeMinutes >= 0) && (duration < freeMinutes)) {
            cost = duration * TariffType.NORMAL.getMinutePrice();
        } else if ((freeMinutes >= 0) && (duration > freeMinutes)) {
            /*
            Если длительность звонка больше, чем количество бесплатных минут, то стоимость считается следующим образом:
            за первые бесплатные минуты плата не взимается, а за оставшееся время стоимость составляет 1.5 рубля за минуту.
             */
            cost = freeMinutes * TariffType.NORMAL.getMinutePrice() + calculatePerMinuteCost(duration - freeMinutes);
        } else {
            /*
            Если минуты закончились, то считается по тарифу поминутный
             */
            cost = calculatePerMinuteCost(duration);
        }
        return cost;
    }

    private static String formatDateTime(LocalDateTime dt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss0");
        return dt.format(formatter);
    }
}
