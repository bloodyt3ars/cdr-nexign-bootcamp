package ru.homework.cdrtest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.homework.cdrtest.component.BillingRealTime;
import ru.homework.cdrtest.dto.AbonentDto;
import ru.homework.cdrtest.dto.BillingDto;
import ru.homework.cdrtest.dto.PayDto;
import ru.homework.cdrtest.dto.TariffDto;
import ru.homework.cdrtest.entity.Abonent;
import ru.homework.cdrtest.entity.PhoneNumber;
import ru.homework.cdrtest.entity.TariffType;
import ru.homework.cdrtest.repository.AbonentRepository;
import ru.homework.cdrtest.repository.PhoneNumberRepository;
import ru.homework.cdrtest.swagger.BillingResponse;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("manager")
@Tag(name = "manager", description = "В этом разделе находятся методы взаимодействия менеджера с системой")
public class ManagerController {


    PhoneNumberRepository phoneNumberRepository;
    BillingRealTime billingRealTime;
    AbonentRepository abonentRepository;

    public ManagerController(PhoneNumberRepository phoneNumberRepository, BillingRealTime billingRealTime, AbonentRepository abonentRepository) {
        this.phoneNumberRepository = phoneNumberRepository;
        this.billingRealTime = billingRealTime;
        this.abonentRepository = abonentRepository;
    }


    @PatchMapping("changeTariff")
    @Operation(summary = "Менеджер изменяет тариф абонента",
            description = "Менеджер изменяет тариф абонента",
            operationId = "managerChangeTariff")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = TariffDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content())})
    public ResponseEntity<?> changeTariff(@RequestBody
                                              @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "В теле запроса обязательно должен быть номер телефона и тариф")
                                              TariffDto tariffDto) {
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
    @Operation(summary = "Менеджер создаёт нового абонента",
            description = "Менеджер создаёт нового абонента",
            operationId = "managerAbonent")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = AbonentDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content())})
    public ResponseEntity<?> createNewAbonent(@RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "В теле запроса обязательно должен быть логин абонента, его номер, тариф и сумма, на которую абонент планирует пополнить баланс")
                                                  AbonentDto abonentDto) {

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
    @Operation(summary = "Менеджер проводит тарификацию",
            description = "Менеджер проводит тарификацию",
            operationId = "managerBilling")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = BillingResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content())})
    public ResponseEntity<?> billing(@RequestBody
                                         @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "В теле запроса должен быть action")
                                         BillingDto billingDto) {
        if (!billingDto.getAction().equals("run")) {
            return new ResponseEntity<>("unknown action", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(billingRealTime.billing(), HttpStatus.OK);
    }
}
