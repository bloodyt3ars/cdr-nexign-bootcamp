package ru.homework.cdrtest.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.homework.cdrtest.entity.Role;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, Integer> {
    /*
    принимает значение типа Role и возвращает его порядковый номер,
    увеличенный на единицу, так как enum в Java индексируется с нуля.
     */
    @Override
    public Integer convertToDatabaseColumn(Role role) {
        return (role.ordinal()+1);
    }

    /*
    принимает значение типа Integer и возвращает соответствующий элемент из перечисления Role,
    полученный из массива элементов с помощью переданного значения, уменьшенного на единицу,
    чтобы скомпенсировать индексацию enum с нуля.
     */
    @Override
    public Role convertToEntityAttribute(Integer integer) {
        return Role.values()[integer-1];
    }
}
