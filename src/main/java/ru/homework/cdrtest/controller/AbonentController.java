package ru.homework.cdrtest.controller;

import org.springframework.web.bind.annotation.*;
import ru.homework.cdrtest.component.HighPerfomanceRatingServer;
import ru.homework.cdrtest.entity.PhoneNumber;
import ru.homework.cdrtest.repository.PhoneNumberRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("abonent")
public class AbonentController implements Controller{

    HighPerfomanceRatingServer highPerfomanceRatingServer;
    PhoneNumberRepository phoneNumberRepository;

    public AbonentController(HighPerfomanceRatingServer highPerfomanceRatingServer, PhoneNumberRepository phoneNumberRepository) {
        this.highPerfomanceRatingServer = highPerfomanceRatingServer;
        this.phoneNumberRepository = phoneNumberRepository;
    }

    @PatchMapping("/pay")
    public Map<String, Object> pay(@RequestParam(name = "numberPhone") String numberPhone,@RequestParam(name = "money") int money){
        Map<String, Object> responseBody = new HashMap<>();
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
    @GetMapping("/report/{numberPhone}")
    public List<Map> report(@PathVariable("numberPhone")String numberPhone){
        PhoneNumber phoneNumber = phoneNumberRepository.findPhoneNumberByPhoneNumber(numberPhone);
        if (phoneNumber!=null){
            return highPerfomanceRatingServer.calculate(phoneNumber);
        }
        else {
            List<Map> responseBody = new ArrayList<>();
            Map<String,String> map = new HashMap<>();
            map.put("exception", "phone number not found");
            responseBody.add(map);
            return responseBody;
        }

    }
}
