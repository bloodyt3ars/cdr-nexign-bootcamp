package ru.homework.cdrtest.role;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "role")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "sysName")
    private Role role;
}
