package ru.homework.cdrtest.entity;

public enum TariffType {

    UNLIMITED_300("06","Безлимит 300", 1, 300, 100),
    PER_MINUTE("03","Поминутный", 1.5, 0, 0),
    NORMAL("11","Обычный", 0.5, 100, 0);
    private  String code;
    private String name;
    private double minutePrice;
    private int freeMinutes;
    private double fixedPrice;

    TariffType() {

    }

    TariffType(String code, String name, double minutePrice, int freeMinutes, double fixedPrice) {
        this.code = code;
        this.name = name;
        this.minutePrice = minutePrice;
        this.freeMinutes = freeMinutes;
        this.fixedPrice = fixedPrice;
    }

    public String getCode() {
        return code;
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
