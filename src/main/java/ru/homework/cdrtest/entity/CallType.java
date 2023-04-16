package ru.homework.cdrtest.entity;

public enum CallType {
    OUTGOING("Исходящий","01"),
    INCOMING("Входящий","02");
    private String name;
    private String code;

    CallType(String name, String code){
        this.code = code;
        this.name = name;
    }

    public String getName(){
        return name;
    }
    public String getCode(){
        return code;
    }
}
