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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.homework.cdrtest.component.HighPerformanceRatingServer;
import ru.homework.cdrtest.dto.PayDto;
import ru.homework.cdrtest.entity.Abonent;
import ru.homework.cdrtest.entity.PhoneNumber;
import ru.homework.cdrtest.repository.AbonentRepository;
import ru.homework.cdrtest.repository.PhoneNumberRepository;
import ru.homework.cdrtest.swagger.ReportResponse;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("abonent")
@Tag(name="abonent", description="В этом разделе находятся методы взаимодействия абонента с системой")
public class AbonentController {

    private final HighPerformanceRatingServer highPerfomanceRatingServer;
    private final PhoneNumberRepository phoneNumberRepository;
    private final AbonentRepository abonentRepository;


    public AbonentController(HighPerformanceRatingServer highPerfomanceRatingServer,
                             PhoneNumberRepository phoneNumberRepository,
                             AbonentRepository abonentRepository) {
        this.highPerfomanceRatingServer = highPerfomanceRatingServer;
        this.phoneNumberRepository = phoneNumberRepository;
        this.abonentRepository = abonentRepository;
    }

    @Operation(
            summary = "Пополнение баланса абонентом",
            description = "Абонент пополняет свой счет",
            operationId = "abonentPay"
    )
    @PatchMapping("pay")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = PayDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content()),
            @ApiResponse(responseCode = "400", description = "Phone number not found")})
    public ResponseEntity<Map<String, Object>> pay(@RequestBody
                                                       @Parameter(description = "В теле запроса обязательно должен быть номер абонента и сумма, на которую абонент планирует пополнить баланс")
                                                       PayDto payDto) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        PhoneNumber phoneNumber = phoneNumberRepository.findPhoneNumberByPhoneNumber(payDto.getNumberPhone());
        int money = payDto.getMoney();
        if (phoneNumber != null) {
            phoneNumber.setBalance(phoneNumber.getBalance() + money);
            phoneNumberRepository.save(phoneNumber);
            responseBody.put("id", phoneNumber.getId());
            responseBody.put("numberPhone", phoneNumber.getPhoneNumber());
            responseBody.put("money", phoneNumber.getBalance());
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        } else {
            responseBody.put("exception", "phone number not found");
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
            summary = "Получение отчета",
            description = "Абонент получает отчет по своему номеру телефона за прошедший месяц",
            operationId = "abonentReport"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = ReportResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "400", description = "Something going wrong. Perhaps you do not have access to this phone number"),
            @ApiResponse(responseCode = "400", description = "Phone number not found")})
    @GetMapping("report/{numberPhone}")
    public ResponseEntity<?> report(@PathVariable("numberPhone")
            @Parameter(description = "Номер телефона")
                                        String numberPhone) {
        PhoneNumber phoneNumber = phoneNumberRepository.findPhoneNumberByPhoneNumber(numberPhone);
        if (phoneNumber == null) {
            return new ResponseEntity<>("exception, phone number not found", HttpStatus.BAD_REQUEST);
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Abonent abonent = abonentRepository.findByUsername(username).orElse(null);
        List<PhoneNumber> phoneNumbers = phoneNumberRepository.findAllByAbonent(abonent);
        if ((abonent != null)&&(phoneNumber.getAbonent()==null)) {//Тестовая логика
            return new ResponseEntity<>(highPerfomanceRatingServer.calculate(phoneNumber), HttpStatus.UNAUTHORIZED);
        }
        for (PhoneNumber number : phoneNumbers) {
            if (number.getPhoneNumber().equals(numberPhone)) {
                return new ResponseEntity<>(highPerfomanceRatingServer.calculate(number), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Something going wrong. Perhaps you do not have access to this phone number", HttpStatus.BAD_REQUEST);
    }

}
