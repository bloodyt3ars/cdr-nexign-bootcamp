package ru.homework.cdrtest.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "phone_number_balance_history")
public class PhoneNumberBalanceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "balance")
    private int balance;
    @Column(name = "timestamp")
    private Timestamp timestamp;
    @ManyToOne
    @JoinColumn(name = "phone_number_id")
    private PhoneNumber phoneNumber;

    public PhoneNumberBalanceHistory(Long id, int balance, Timestamp timestamp, PhoneNumber phoneNumber) {
        this.id = id;
        this.balance = balance;
        this.timestamp = timestamp;
        this.phoneNumber = phoneNumber;
    }

    public PhoneNumberBalanceHistory() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
