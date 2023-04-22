package ru.homework.cdrtest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.homework.cdrtest.component.HighPerfomanceRatingServer;
import ru.homework.cdrtest.dto.PayDto;
import ru.homework.cdrtest.entity.Abonent;
import ru.homework.cdrtest.entity.PhoneNumber;
import ru.homework.cdrtest.repository.AbonentRepository;
import ru.homework.cdrtest.repository.PhoneNumberRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("abonent")
public class AbonentController {

    private final HighPerfomanceRatingServer highPerfomanceRatingServer;
    private final PhoneNumberRepository phoneNumberRepository;
    private final AbonentRepository abonentRepository;


    public AbonentController(HighPerfomanceRatingServer highPerfomanceRatingServer,
                             PhoneNumberRepository phoneNumberRepository,
                             AbonentRepository abonentRepository) {
        this.highPerfomanceRatingServer = highPerfomanceRatingServer;
        this.phoneNumberRepository = phoneNumberRepository;
        this.abonentRepository = abonentRepository;
    }

    @PatchMapping("pay")
    public ResponseEntity<Map<String, Object>> pay(@RequestBody PayDto payDto) {
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

    @GetMapping("report/{numberPhone}")
    public ResponseEntity<?> report(@PathVariable("numberPhone") String numberPhone) {
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
