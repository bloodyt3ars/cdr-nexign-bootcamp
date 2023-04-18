package ru.homework.cdrtest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.homework.cdrtest.component.BillingRealTime;
import ru.homework.cdrtest.entity.PhoneNumber;
import ru.homework.cdrtest.entity.TariffType;
import ru.homework.cdrtest.repository.AbonentRepository;
import ru.homework.cdrtest.repository.PhoneNumberRepository;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("manager")
public class ManagerController implements Controller {


    PhoneNumberRepository phoneNumberRepository;
    BillingRealTime billingRealTime;
    AbonentRepository abonentRepository;

    public ManagerController(PhoneNumberRepository phoneNumberRepository, BillingRealTime billingRealTime, AbonentRepository abonentRepository) {
        this.phoneNumberRepository = phoneNumberRepository;
        this.billingRealTime = billingRealTime;
        this.abonentRepository = abonentRepository;
    }


    @PatchMapping("chaneTariff")
    public ResponseEntity<?> changeTariff(@RequestBody Map<String, Object> request) {
        String numberPhone = (String) request.get("numberPhone");
        String tariffId = (String) request.get("tariff_id");
        Map<String, Object> responseBody = new LinkedHashMap<>();
        PhoneNumber phoneNumber = phoneNumberRepository.findPhoneNumberByPhoneNumber(numberPhone);
        TariffType tariffType = null;
        if (phoneNumber != null) {
            for (TariffType type : TariffType.values()) {
                if (type.getCode().equals(tariffId)) {
                    tariffType = type;
                }
            }
            if (tariffType == null) {
                responseBody.put("exception", "tariff not found");
            } else {
                phoneNumber.setTariffType(tariffType);
                phoneNumberRepository.save(phoneNumber);
                responseBody.put("id", phoneNumber.getId());
                responseBody.put("numberPhone", phoneNumber.getPhoneNumber());
                responseBody.put("money", phoneNumber.getBalance());
            }

        } else {
            responseBody.put("exception", "phone number not found");
        }
        return ResponseEntity.ok(responseBody);
    }


    @PostMapping("abonent")
    public Map<String, Object> createNewAbonent(@RequestBody Map<String, Object> request) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        if (request.containsKey("numberPhone") && request.containsKey("tariff_id") && request.containsKey("balance")) {
            String numberPhone = (String) request.get("numberPhone");
            String tariffId = (String) request.get("tariff_id");
            int balance = (int) request.get("balance");
            PhoneNumber phoneNumber = new PhoneNumber();
            phoneNumber.setTariffType(tariffId);
            phoneNumber.setAbonent(abonentRepository.getById(1L));
            if (phoneNumber.getTariffType() == null) {
                responseBody.put("exception", "tariff not found");
            } else if (phoneNumberRepository.findPhoneNumberByPhoneNumber(numberPhone) != null) {
                responseBody.put("exception", "phone number already exist");
            } else if (balance < 0) {
                responseBody.put("exception", "balance cannot be negative");
            } else {
                phoneNumber.setPhoneNumber(numberPhone);
                phoneNumber.setBalance(balance);
                phoneNumberRepository.save(phoneNumber);//номер телефона уникальный, надо обработать уже имеющихся значений
                responseBody.put("numberPhone", phoneNumber.getPhoneNumber());
                responseBody.put("tariff_id", phoneNumber.getTariffType().getCode());
                responseBody.put("balance", phoneNumber.getBalance());
            }
        } else {
            responseBody.put("exception", "miss argument");
        }
        return responseBody;
    }

    @PatchMapping("billing")
    public Map<String, Object> billing(@RequestBody Map<String, Object> request) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        String action = (String) request.get("action");
        if (action.equals("run")) {
            responseBody = billingRealTime.billing();
        } else {
            responseBody.put("exception", "unknown action");
        }
        return responseBody;
    }
}
