package ru.homework.cdrtest.entity;

import jakarta.persistence.Entity;


public enum TariffType {

    UNLIMITED_300("Безлимит 300", 1, 300, 100),
    PER_MINUTE("Поминутный", 1.5, 0, 0),
    NORMAL("Обычный", 0.5, 100, 0);
    private String name;
    private double minutePrice;
    private int freeMinutes;
    private double fixedPrice;
    TariffType(String name, double minutePrice, int freeMinutes,double fixedPrice){
        this.name = name;
        this.minutePrice = minutePrice;
        this.freeMinutes = freeMinutes;
        this.fixedPrice = fixedPrice;
    }

    TariffType() {

    }

    public String getName() {
        return name;
    }

    public double getMinutePrice() {
        return minutePrice;
    }

    public int getFreeMinutes() {
        return freeMinutes;
    }

    public double getFixedPrice() {
        return fixedPrice;
    }
}
