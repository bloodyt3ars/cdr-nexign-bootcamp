package ru.homework.cdrtest.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.homework.cdrtest.component.HighPerfomanceRatingServer;
import ru.homework.cdrtest.entity.Abonent;
import ru.homework.cdrtest.entity.PhoneNumber;
import ru.homework.cdrtest.repository.AbonentRepository;
import ru.homework.cdrtest.repository.PhoneNumberRepository;

import java.util.*;

@RestController
@RequestMapping("abonent")
public class AbonentController{

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

    @PatchMapping( "pay")
    public  Map<String, Object> pay(@RequestBody Map<String, Object> request){
        String numberPhone = (String) request.get("numberPhone");
        int money = (int) request.get("money");
        Map<String, Object> responseBody = new LinkedHashMap<>();
        PhoneNumber phoneNumber = phoneNumberRepository.findPhoneNumberByPhoneNumber(numberPhone);
        if (phoneNumber!=null){
            phoneNumber.setBalance(phoneNumber.getBalance()+money);
            phoneNumberRepository.save(phoneNumber);
            responseBody.put("id", phoneNumber.getId());
            responseBody.put("numberPhone", phoneNumber.getPhoneNumber());
            responseBody.put("money", phoneNumber.getBalance());
        }
        else {
            responseBody.put("exception", "phone number not found");
        }
        return responseBody;
    }
    @GetMapping("report/{numberPhone}")
    public List<Map> report(@PathVariable("numberPhone")String numberPhone){
        PhoneNumber phoneNumber = phoneNumberRepository.findPhoneNumberByPhoneNumber(numberPhone);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Abonent abonent = abonentRepository.findByUsername(username).orElse(null);
        List<PhoneNumber> phoneNumbers = phoneNumberRepository.findAllByAbonent(abonent);
        if (phoneNumber!=null){
            for (PhoneNumber number : phoneNumbers) {
                if (number.getPhoneNumber().equals(numberPhone)) {
                    return highPerfomanceRatingServer.calculate(number);
                }
            }
        }
        else {
            List<Map> responseBody = new ArrayList<>();
            Map<String,String> map = new LinkedHashMap<>();
            map.put("exception", "phone number not found");
            responseBody.add(map);
            return responseBody;
        }
        return null;
    }
}
