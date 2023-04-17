package ru.homework.cdrtest.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.homework.cdrtest.entity.Role;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Role role) {
        return (role.ordinal()+1);
    }

    @Override
    public Role convertToEntityAttribute(Integer integer) {
        return Role.values()[integer-1];
    }
}
