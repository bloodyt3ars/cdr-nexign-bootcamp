package ru.homework.cdrtest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.homework.cdrtest.component.BillingRealTime;
import ru.homework.cdrtest.dto.AbonentDto;
import ru.homework.cdrtest.dto.BillingDto;
import ru.homework.cdrtest.dto.TariffDto;
import ru.homework.cdrtest.entity.Abonent;
import ru.homework.cdrtest.entity.PhoneNumber;
import ru.homework.cdrtest.entity.TariffType;
import ru.homework.cdrtest.repository.AbonentRepository;
import ru.homework.cdrtest.repository.PhoneNumberRepository;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("manager")
public class ManagerController {


    PhoneNumberRepository phoneNumberRepository;
    BillingRealTime billingRealTime;
    AbonentRepository abonentRepository;

    public ManagerController(PhoneNumberRepository phoneNumberRepository, BillingRealTime billingRealTime, AbonentRepository abonentRepository) {
        this.phoneNumberRepository = phoneNumberRepository;
        this.billingRealTime = billingRealTime;
        this.abonentRepository = abonentRepository;
    }


    @PatchMapping("chaneTariff")
    public ResponseEntity<?> changeTariff(@RequestBody TariffDto tariffDto) {
        String numberPhone = tariffDto.getNumberPhone();
        String tariffId = tariffDto.getTariff_id();
        TariffType tariffType = TariffType.getTariffTypeByTariffId(tariffId);
        if (tariffType == null) {
            return new ResponseEntity<>("invalid tariff type", HttpStatus.BAD_REQUEST);
        }
        PhoneNumber phoneNumber = phoneNumberRepository.findPhoneNumberByPhoneNumber(numberPhone);
        if (phoneNumber == null) {
            return new ResponseEntity<>("phone number not found", HttpStatus.BAD_REQUEST);
        }
        Map<String, Object> responseBody = new LinkedHashMap<>();
        phoneNumber.setTariffType(tariffType);
        phoneNumberRepository.save(phoneNumber);
        responseBody.put("id", phoneNumber.getId());
        responseBody.put("numberPhone", phoneNumber.getPhoneNumber());
        responseBody.put("money", phoneNumber.getBalance());

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }


    @PostMapping("abonent")
    public ResponseEntity<?> createNewAbonent(@RequestBody AbonentDto abonentDto) {

        Map<String, Object> responseBody = new LinkedHashMap<>();
        String username = abonentDto.getUsername();
        String numberPhone = abonentDto.getNumberPhone();
        if (phoneNumberRepository.findPhoneNumberByPhoneNumber(numberPhone) != null) {
            return new ResponseEntity<>("phone number already exist", HttpStatus.BAD_REQUEST);
        }
        String tariffId = abonentDto.getTariff_id();
        TariffType tariffType = TariffType.getTariffTypeByTariffId(tariffId);
        if (tariffType == null) {
            return new ResponseEntity<>("invalid tariff", HttpStatus.BAD_REQUEST);
        }
        int balance = abonentDto.getBalance();
        if (balance < 0) {
            return new ResponseEntity<>("balance cannot be negative", HttpStatus.BAD_REQUEST);
        }
        Abonent abonent = abonentRepository.findByUsername(username).orElse(null);
        if (abonent == null) {
            return new ResponseEntity<>("invalid username", HttpStatus.BAD_REQUEST);
        }
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setAbonent(abonent);
        phoneNumber.setPhoneNumber(numberPhone);
        phoneNumber.setBalance(balance);
        phoneNumber.setTariffType(tariffType);

        responseBody.put("username", abonent.getUsername());
        responseBody.put("numberPhone", phoneNumber.getPhoneNumber());
        responseBody.put("tariff_id", phoneNumber.getTariffType().getCode());
        responseBody.put("balance", phoneNumber.getBalance());

        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @PatchMapping("billing")
    public ResponseEntity<?> billing(@RequestBody BillingDto billingDto) {
        if (!billingDto.getAction().equals("run")) {
            return new ResponseEntity<>("unknown action", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(billingRealTime.billing(), HttpStatus.OK);
    }
}
