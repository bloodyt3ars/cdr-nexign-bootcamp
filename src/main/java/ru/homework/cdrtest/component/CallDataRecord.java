package ru.homework.cdrtest.component;

import org.springframework.stereotype.Component;
import ru.homework.cdrtest.entity.CallRecord;
import ru.homework.cdrtest.entity.CallType;
import ru.homework.cdrtest.entity.PhoneNumber;
import ru.homework.cdrtest.repository.CallRecordRepository;
import ru.homework.cdrtest.repository.PhoneNumberRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
public class CallDataRecord {
    private final PhoneNumberRepository phoneNumberRepository;
    private final CallRecordRepository callRecordRepository;


    private static int month = 0;//Статическая переменная, отвечающая за месяц в котором генерируются звонки
    private static int year = 2022;

    public CallDataRecord(PhoneNumberRepository phoneNumberRepository, CallRecordRepository callRecordRepository) {
        this.phoneNumberRepository = phoneNumberRepository;
        this.callRecordRepository = callRecordRepository;
    }

    /*
    Генерация CDR.
    */

    /*public void generateCDR() {
        monthSetup();
        List<CallRecord> callRecords = new ArrayList<>();
        List<PhoneNumber> phoneNumbers = phoneNumberRepository.findAllByBalanceGreaterThan(0);
        for (PhoneNumber phoneNumber : phoneNumbers) {
            List<CallRecord> outgoingCalls = callRecordRepository.findAllByPhoneNumber(phoneNumber);
            List<CallRecord> incomingCalls = callRecordRepository.findAllByReceivingPhoneNumber(phoneNumber);
            for (PhoneNumber receivingPhoneNumber : phoneNumbers) {
                if (receivingPhoneNumber.equals(phoneNumber))
                    continue;
                List<CallRecord> incomingCallsToThisNumber = callRecordRepository.findAllByReceivingPhoneNumber(receivingPhoneNumber);
                List<CallRecord> outgoingCallsToThisNumber = callRecordRepository.findAllByPhoneNumber(receivingPhoneNumber);
                for (int i = 0; i < outgoingCalls.size(); i++) {
                    CallRecord outgoingCall = outgoingCalls.get(i);
                    if (outgoingCall.getCallType() == CallType.OUTGOING && outgoingCall.getReceivingPhoneNumber() == null) {
                        LocalDateTime outgoingCallStart = outgoingCall.getCallStart();
                        LocalDateTime outgoingCallEnd = outgoingCall.getCallEnd();
                        boolean isValid = true;
                        for (int j = 0; j < incomingCalls.size(); j++) {
                            CallRecord incomingCall = incomingCalls.get(j);
                            if (incomingCall.getCallStart().isBefore(outgoingCallEnd) && incomingCall.getCallEnd().isAfter(outgoingCallStart)) {
                                isValid = false;
                                break;
                            }
                        }
                        for (int j = 0; j < incomingCallsToThisNumber.size(); j++) {
                            CallRecord incomingCall = incomingCallsToThisNumber.get(j);
                            if (incomingCall.getCallStart().isBefore(outgoingCallEnd) && incomingCall.getCallEnd().isAfter(outgoingCallStart)) {
                                isValid = false;
                                break;
                            }
                        }
                        for (int j = 0; j < outgoingCallsToThisNumber.size(); j++) {
                            CallRecord outgoingCallToThisNumber = outgoingCallsToThisNumber.get(j);
                            if (outgoingCallToThisNumber.getCallStart().isBefore(outgoingCallEnd) && outgoingCallToThisNumber.getCallEnd().isAfter(outgoingCallStart)) {
                                isValid = false;
                                break;
                            }
                        }
                        if (isValid) {
                            CallRecord incomingCall = new CallRecord();
                            incomingCall.setPhoneNumber(outgoingCall.getReceivingPhoneNumber());
                            incomingCall.setReceivingPhoneNumber(outgoingCall.getPhoneNumber());
                            incomingCall.setCallType(CallType.INCOMING);
                            incomingCall.setCallStart(outgoingCallStart.plusSeconds(1));
                            LocalDateTime incomingCallEnd = outgoingCallEnd.plusSeconds(1);
                            incomingCall.setCallEnd(incomingCallEnd);
                            outgoingCall.setReceivingPhoneNumber(receivingPhoneNumber);
                            outgoingCall.setCallEnd(outgoingCallEnd.plusSeconds(1));
                            callRecords.add(incomingCall);
                            callRecords.add(outgoingCall);
                            callRecordRepository.save(incomingCall);
                            callRecordRepository.save(outgoingCall);
                        }
                    }
                }
            }
        }
    }*/
    public void generateCDR() {
        monthSetup();// Изменение статической переменной месяца на +1.
        List<CallRecord> callRecords = new ArrayList<>();//Создается ArrayList звонков
        //Берутся все телефоны, чей баланс больше нуля. Ведь зачем создавать звонки для тех, у кого баланс меньше 0. Они же не могут звонить?
        List<PhoneNumber> phoneNumbers = phoneNumberRepository.findAllByBalanceGreaterThan(0);
        for (PhoneNumber phoneNumber : phoneNumbers) {//Цикл в котором мы создаем запись звонка
            Random random = new Random();
            int n = random.nextInt(30); //Сколько мы создаем звонков. От 0 до 30.
            for (int i = 0; i < n; i++) {
                CallRecord callRecord = new CallRecord();//Создается запись
                CallType callType = getRandomCallType(); //Рандомно определяется входящий или исходящий звонок.
                LocalDateTime startTime = getRandomDateTime(phoneNumber); // Рандомное начало
                LocalDateTime endTime = startTime.plusSeconds(getRandomDuration()); //К началу прибавляется рандомное кол-во секунд. От 1 секунды до 60 минут.
                callRecord.setPhoneNumber(phoneNumber); // Присваивается номер телефона
                callRecord.setCallType(callType); // Присваивается тип звонка
                callRecord.setCallStart(startTime);// Присвоение даты начала
                callRecord.setCallEnd(endTime);//Присвоение окончание
                callRecords.add(callRecord);//Добавление в ArrayList
            }
        }

        Collections.sort(callRecords); //Сортировка записей, чтобы они были в порядке совершения звонка
        callRecordRepository.saveAll(callRecords);// сохранение всех записей в бд
    }


    private CallType getRandomCallType() {
        Random random = new Random();
        //Рандомная булевская переменная. Если TRUE, то входящий, в противном случае исходящий.
        return random.nextBoolean() ? CallType.INCOMING : CallType.OUTGOING;
    }

    private static int getRandomDuration() {
        Random random = new Random();
        return random.nextInt(3600) + 1; // звонок длится от 1 секунды до 60 минут
    }

    private LocalDateTime getRandomDateTime(PhoneNumber phoneNumber) {
        Random random = new Random();
        int year = 2022;
        int month = CallDataRecord.month; //Месяц из статической переменной
        int day = random.nextInt(28) + 1; //Рандомный день
        int hour = random.nextInt(24);//Рандомные часы
        int minute = random.nextInt(60);//рандомные минуты
        int second = random.nextInt(60);//рандомные секунды
        LocalDateTime dateTime = LocalDateTime.of(year, month, day, hour, minute, second);
        List<CallRecord> callRecords = callRecordRepository.findAllByMonthAndYearAndPhoneNumber(CallDataRecord.getMonth(),
                CallDataRecord.getYear(), phoneNumber);

        while (true) {//Проверка на то, есть ли уже звонок у этого абонента в это время
            /*Здесь мы используем бесконечный цикл с условием выхода - когда дата/время будет уникальной для данного номера телефона.
            Мы сравниваем новый сгенерированный звонок с уже сгенерированными звонками для этого номера телефона и проверяем,
            не пересекается ли он с ними. Если да, то генерируем новую дату/время и повторяем проверку.
            Если нет, то возвращаем новую дату/время.*/
            boolean unique = true;
            for (CallRecord callRecord : callRecords) {
                if (dateTime.isAfter(callRecord.getCallStart()) &&
                        dateTime.isBefore(callRecord.getCallEnd())) {
                    unique = false;
                    break;
                }
            }
            if (unique) {
                return dateTime;
            } else {
                day = random.nextInt(28) + 1;
                hour = random.nextInt(24);
                minute = random.nextInt(60);
                second = random.nextInt(60);
                dateTime = LocalDateTime.of(year, month, day, hour, minute, second);
            }
        }
    }

    private static void monthSetup() {
        /*
        Инкрементируем переменную месяца, чтобы программой можно было пользоваться не один раз в течении одной сессии и
        было видно историю звонков
        Тарификация производится по месяцам.
        */
        month++;
        if (month > 12) {
            month = month - 12;
            year++;
        }
    }

    public static int getMonth() {
        //Статический метод, который отдает какой сейчас месяц.
        return month;
    }

    public static int getYear() {
        return year;
    }
}
