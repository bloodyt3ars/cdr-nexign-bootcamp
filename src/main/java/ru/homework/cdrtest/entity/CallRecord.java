package ru.homework.cdrtest.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "call_record")
public class CallRecord implements Comparable<CallRecord>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "phone_number_id")
    private PhoneNumber phoneNumber; // номер абонента

    /*@ManyToOne
    @JoinColumn(name = "receiving_phone_number_id")
    private PhoneNumber receivingPhoneNumber; // номер абонента принимающий звонок*/

    @Column(name = "call_type_code")
    private CallType callType; // тип вызова (01 - исходящие, 02 - входящие)
    @Column(name = "call_start")
    private LocalDateTime callStart; // дата и время начала звонка
    @Column(name = "call_end")
    private LocalDateTime callEnd; // дата и время окончания звонка


    public CallRecord() {

    }

    public CallRecord(Long id, PhoneNumber phoneNumber, CallType callType, LocalDateTime callStart, LocalDateTime callEnd) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.callType = callType;
        this.callStart = callStart;
        this.callEnd = callEnd;
    }
    /*public CallRecord(Long id, PhoneNumber phoneNumber, PhoneNumber receivingPhoneNumber, CallType callType, LocalDateTime callStart, LocalDateTime callEnd) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.receivingPhoneNumber = receivingPhoneNumber;
        this.callType = callType;
        this.callStart = callStart;
        this.callEnd = callEnd;
    }*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /*public PhoneNumber getReceivingPhoneNumber() {
        return receivingPhoneNumber;
    }

    public void setReceivingPhoneNumber(PhoneNumber receivingPhoneNumber) {
        this.receivingPhoneNumber = receivingPhoneNumber;
    }*/

    public CallType getCallType() {
        return callType;
    }

    public void setCallType(CallType callType) {
        this.callType = callType;
    }

    public LocalDateTime getCallStart() {
        return callStart;
    }

    public void setCallStart(LocalDateTime callStart) {
        this.callStart = callStart;
    }

    public LocalDateTime getCallEnd() {
        return callEnd;
    }

    public void setCallEnd(LocalDateTime callEnd) {
        this.callEnd = callEnd;
    }

    @Override
    public int compareTo(CallRecord o) {
        if(callStart.compareTo(o.getCallStart()) < 0 )
            return -1;
        if(callStart.compareTo(o.getCallStart()) == 0 )
            return 0;
        return 1;
    }
}
