package ru.homework.cdrtest.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "phone_number")
public class PhoneNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "balance")
    private double balance;

    @ManyToOne
    @JoinColumn(name = "abonent_id")
    private Abonent abonent;

    @Column(name = "tariff_type_id")
    private TariffType tariffType;

    private int freeMinute;


    public PhoneNumber(Long id, String phoneNumber, double balance, Abonent abonent, TariffType tariffType) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.balance = balance;
        this.abonent = abonent;
        this.tariffType = tariffType;
        this.freeMinute = tariffType.getFreeMinutes();
    }

    public PhoneNumber() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Abonent getAbonent() {
        return abonent;
    }

    public void setAbonent(Abonent abonent) {
        this.abonent = abonent;
    }

    public TariffType getTariffType() {
        return tariffType;
    }

    public void setTariffType(TariffType tariffType) {
        this.tariffType = tariffType;
    }

    public void setTariffType(String tariffId){
        for (TariffType type : TariffType.values()) {
            if (type.getCode().equals(tariffId)) {
                this.tariffType = type;
            }
        }
    }
}
