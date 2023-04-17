package ru.homework.cdrtest.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "phone_number_balance_history")
public class PhoneNumberBalanceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "old_balance")
    private int oldBalance;

    @Column(name = "new_balance")
    private int newBalance;
    @Column(name = "change_data")
    private Timestamp change_data;
    @ManyToOne
    @JoinColumn(name = "phone_number_id")
    private PhoneNumber phoneNumber;

    public PhoneNumberBalanceHistory(Long id, int oldBalance, int newBalance, Timestamp change_data, PhoneNumber phoneNumber) {
        this.id = id;
        this.oldBalance = oldBalance;
        this.newBalance = newBalance;
        this.change_data = change_data;
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

    public int getOldBalance() {
        return oldBalance;
    }

    public void setOldBalance(int oldBalance) {
        this.oldBalance = oldBalance;
    }

    public int getNewBalance() {
        return newBalance;
    }

    public void setNewBalance(int newBalance) {
        this.newBalance = newBalance;
    }

    public Timestamp getChange_data() {
        return change_data;
    }

    public void setChange_data(Timestamp change_data) {
        this.change_data = change_data;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
