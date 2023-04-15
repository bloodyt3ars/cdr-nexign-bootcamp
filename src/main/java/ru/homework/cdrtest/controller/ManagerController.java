package ru.homework.cdrtest.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/manager")
public class ManagerController {

    @PatchMapping("chaneTariff")
    public String changeTariff(@RequestParam(name = "numberPhone") String numberPhone,
                               @RequestParam(name = "tariffId") String tariffId){
        return "Tariff change for " + numberPhone + " to " + tariffId;
    }
    @PostMapping("abonent")
    public String createNewAbonent(@RequestParam(name = "numberPhone") String numberPhone,
                                   @RequestParam(name = "tariffId") String tariffId,
                                   @RequestParam(name = "balance") int balance){
        return "new abonent " + numberPhone + " with tarriff " + tariffId + " with balance " + balance;
    }
    @PatchMapping("billing")
    public String billing(@RequestParam(name = "action") String action){
        if (action.equals("run")){
            return "action : run";
        }
        else {
            return "null";
        }
    }
}
