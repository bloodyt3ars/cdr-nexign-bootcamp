package ru.homework.cdrtest.tarifftype;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tariff_type")
public class TariffTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "price_per_min")
    private Double minutePrice;

    @Column(name = "free_minutes")
    private Long freeMinutes;

    @Column(name = "fixed_price")
    private Double fixedPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "tariff_type")
    private TariffType tariffType;
}
