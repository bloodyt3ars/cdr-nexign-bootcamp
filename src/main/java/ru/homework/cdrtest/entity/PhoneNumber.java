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
    @Column(name = "current_balance")
    private int balance;

    @ManyToOne
    @JoinColumn(name = "abonent_id")
    private Abonent abonent;

    @Column(name = "tariff_type_id")
    @Enumerated(EnumType.STRING)
    private TariffType tariffType;


    public PhoneNumber(Long id, String phoneNumber, int balance, Abonent abonent, TariffType tariffType) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.balance = balance;
        this.abonent = abonent;
        this.tariffType = tariffType;
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

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
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
}
