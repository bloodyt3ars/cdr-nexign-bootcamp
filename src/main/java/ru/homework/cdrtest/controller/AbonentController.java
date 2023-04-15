package ru.homework.cdrtest.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/abonent")
public class AbonentController {

    @GetMapping
    public Map<String, String> hello(){
        Map<String, String> hello = new HashMap<>();
        hello.put("Hello", "World");
        return hello;
    }
    @PatchMapping("/pay")
    public String pay(@RequestParam(name = "numberPhone") String numberPhone,@RequestParam(name = "money") int money){
        return numberPhone + " " + money;
    }
    @GetMapping("/report/{numberPhone}")
    public String report(@PathVariable("numberPhone") String numberPhone){
        return "report for " + numberPhone;
    }
}
