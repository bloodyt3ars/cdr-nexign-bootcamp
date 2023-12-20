package ru.homework.cdrtest.callrecord;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.homework.cdrtest.abonent.AbonentEntity;

import java.time.Duration;
import java.time.Instant;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "call_record")
public class CallRecordEntity implements Comparable<CallRecordEntity> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "abonent_id")
    private AbonentEntity abonent;

    @Enumerated(EnumType.STRING)
    @Column(name = "call_type_code")
    private CallType callType;

    @Column(name = "call_start")
    private Instant callStart;

    @Column(name = "call_end")
    private Instant callEnd;

    @Column(name = "call_duration")
    private Duration duration;

    @Column(name = "call_cost")
    private Double cost;


    @Override
    public int compareTo(CallRecordEntity o) {
        if (callStart.compareTo(o.getCallStart()) < 0)
            return -1;
        if (callStart.compareTo(o.getCallStart()) == 0)
            return 0;
        return 1;
    }
}
