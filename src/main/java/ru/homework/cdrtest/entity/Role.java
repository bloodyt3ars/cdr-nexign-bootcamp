package ru.homework.cdrtest.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "role")
public class Role {
    private @Id
    @GeneratedValue(strategy = GenerationType.AUTO) long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
