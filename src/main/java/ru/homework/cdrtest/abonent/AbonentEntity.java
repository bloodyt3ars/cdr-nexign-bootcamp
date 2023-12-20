package ru.homework.cdrtest.abonent;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.homework.cdrtest.role.RoleEntity;
import ru.homework.cdrtest.tarifftype.TariffTypeEntity;

import java.util.Set;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "abonent")
public class AbonentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "password")
    private String password;

    @Column(name = "balance")
    private Double balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "tariff_type_id")
    private TariffTypeEntity tariffType;

    @Column(name = "free_minutes")
    private Integer freeMinutes;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "abonent_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<RoleEntity> roleEntities;
}
