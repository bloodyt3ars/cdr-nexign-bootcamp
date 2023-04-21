package ru.homework.cdrtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.homework.cdrtest.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}